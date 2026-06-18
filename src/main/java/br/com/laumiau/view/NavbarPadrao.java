package br.com.laumiau.view;

import laumiau.service.AnimalService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class NavbarPadrao extends JPanel {

    private final AnimalService animalService;
    private final String itemAtivo;

    public NavbarPadrao(String itemAtivo, AnimalService animalService) {
        this.animalService = animalService;
        this.itemAtivo = itemAtivo;
        init();
    }

    public NavbarPadrao(String itemAtivo) {
        this.animalService = null;
        this.itemAtivo = itemAtivo;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(AppTheme.BRANCO);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDA),
                new EmptyBorder(0, 40, 0, 40)
        ));
        setPreferredSize(new Dimension(0, 52));

        add(criarLogo(), BorderLayout.WEST);

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        menu.setOpaque(false);
        menu.add(criarItemMenu("Home",      itemAtivo.equals("Home")));
        menu.add(criarItemMenu("Animais",   itemAtivo.equals("Animais")));
        menu.add(criarItemMenu("Sobre nós", itemAtivo.equals("Sobre nós")));
        add(menu, BorderLayout.CENTER);

        // Chamando o novo botão de Login no canto direito
        add(criarBotaoLogin(), BorderLayout.EAST);
    }

    private JLabel criarLogo() {
        java.net.URL url = getClass().getResource("/img/logoLAUMIAU.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(140, 35, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        }
        JLabel logo = new JLabel("LAU 🐾 MIAU");
        logo.setFont(new Font("SansSerif", Font.BOLD, 26));
        logo.setForeground(AppTheme.LARANJA);
        return logo;
    }

    private JPanel criarItemMenu(String texto, boolean ativo) {
        JPanel pill = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (ativo) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(AppTheme.LARANJA);
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        pill.setOpaque(false);
        pill.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JLabel lbl = new JLabel(texto);
        lbl.setFont(AppTheme.FONTE_LABEL);
        lbl.setForeground(ativo ? AppTheme.BRANCO : new Color(90, 80, 70));
        lbl.setBorder(new EmptyBorder(5, 18, 5, 18));
        pill.add(lbl);

        if (!ativo) {
            pill.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            MouseAdapter ma = new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { lbl.setForeground(AppTheme.LARANJA); }
                @Override public void mouseExited(MouseEvent e)  { lbl.setForeground(new Color(90, 80, 70)); }
                @Override public void mouseClicked(MouseEvent e) {
                    Window pai = SwingUtilities.getWindowAncestor(NavbarPadrao.this);
                    SwingUtilities.invokeLater(() -> {
                        switch (texto) {
                            case "Home" -> {
                                if (animalService != null) {
                                    try {
                                        new AnimalView(animalService);
                                        if (pai != null) pai.dispose();
                                    } catch (Exception erro) {
                                        JOptionPane.showMessageDialog(null, "Erro ao carregar a Home: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                            case "Animais" -> {
                                if (animalService != null) {
                                    try {
                                        new AnimaisCadastradosView(animalService);
                                        if (pai != null) pai.dispose();
                                    } catch (Exception erro) {
                                        JOptionPane.showMessageDialog(null, "Erro ao carregar o painel de animais: " + erro.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
                                        erro.printStackTrace();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "O serviço de animais não foi inicializado corretamente.");
                                }
                            }
                            case "Sobre nós" -> {
                                try {
                                    new SobreNosFrame(animalService).setVisible(true);
                                } catch (Exception erro) {
                                    JOptionPane.showMessageDialog(null, "Erro ao abrir Sobre Nós: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    });
                }
            };
            pill.addMouseListener(ma);
            lbl.addMouseListener(ma);
        }
        return pill;
    }

    private JButton criarBotaoLogin() {
        JButton btn = new JButton("  Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? AppTheme.LARANJA_HOVER : AppTheme.LARANJA);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(AppTheme.FONTE_LABEL);
        btn.setForeground(AppTheme.BRANCO);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(6, 20, 6, 20));

        btn.addActionListener(e -> {
            Window pai = SwingUtilities.getWindowAncestor(NavbarPadrao.this);
            if (pai != null) {
                pai.dispose();
            }
            new LoginView().setVisible(true);
        });

        return btn;
    }
}