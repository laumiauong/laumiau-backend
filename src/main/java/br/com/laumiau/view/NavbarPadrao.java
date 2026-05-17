package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class NavbarPadrao extends JPanel {

    private static final Color LARANJA = new Color(255, 107, 43);
    private static final Color LARANJA_HOVER = new Color(255, 140, 80);
    private static final Color TEXTO = new Color(15, 23, 42);

    public NavbarPadrao(String itemAtivo) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 228, 220)),
                new EmptyBorder(0, 40, 0, 40)
        ));
        setPreferredSize(new Dimension(0, 68));

        // Logo
        JLabel logo = criarLogo();
        add(logo, BorderLayout.WEST);

        // Menu central
        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        menu.setOpaque(false);
        menu.add(criarItemMenu("Home", itemAtivo.equals("Home")));
        menu.add(criarItemMenu("Animais", itemAtivo.equals("Animais")));
        menu.add(criarItemMenu("Sobre nós", itemAtivo.equals("Sobre nós")));
        add(menu, BorderLayout.CENTER);

        // Botão Admin
        add(criarBotaoAdmin(), BorderLayout.EAST);
    }

    private JLabel criarLogo() {
        java.net.URL url = getClass().getResource("/img/logoLAUMIAU.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(160, 50, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        }
        JLabel logo = new JLabel("LAU 🐾 MIAU");
        logo.setFont(new Font("SansSerif", Font.BOLD, 26));
        logo.setForeground(LARANJA);
        return logo;
    }

    private JPanel criarItemMenu(String texto, boolean ativo) {
        JPanel pill = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (ativo) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(LARANJA);
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        pill.setOpaque(false);
        pill.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(ativo ? Color.WHITE : new Color(90, 80, 70));
        lbl.setBorder(new EmptyBorder(8, 18, 8, 18));
        pill.add(lbl);

        if (!ativo) {
            pill.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pill.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { lbl.setForeground(LARANJA); }
                @Override
                public void mouseExited(MouseEvent e) { lbl.setForeground(new Color(90, 80, 70)); }
                @Override
                public void mouseClicked(MouseEvent e) { navegarPara(texto); }
            });
        }
        return pill;
    }

    private void navegarPara(String destino) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        switch (destino) {
            case "Home" -> {
                // Navegação para AnimalView - implemente conforme necessário
            }
            case "Animais" -> {
                // Navegação para AnimaisCadastradosView
            }
            case "Sobre nós" -> new SobreNosFrame().setVisible(true);
        }
        if (frame != null) frame.dispose();
    }

    private JButton criarBotaoAdmin() {
        JButton btn = new JButton("  Admin") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? LARANJA_HOVER : LARANJA);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
                g2.dispose();
                super.paintComponent(g);
            }
            @Override
            protected void paintBorder(Graphics g) {}
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(9, 20, 9, 20));
        btn.addActionListener(e -> new LoginAdmin().setVisible(true));
        return btn;
    }
}