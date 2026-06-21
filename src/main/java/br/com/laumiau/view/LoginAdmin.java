package br.com.laumiau.view;

import laumiau.controller.LoginController;
import laumiau.service.UsuarioService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginAdmin extends JFrame {

    private static final Color LARANJA_BASE = new Color(255, 153, 0);
    private static final Color LARANJA_DARK = new Color(249, 115, 22);
    private static final Color FUNDO        = new Color(255, 251, 245);
    private static final Color TEXTO_DARK   = new Color(31, 41, 55);
    private static final Color BORDAS_LEVES = new Color(230, 230, 230);
    private static final Color BRANCO       = Color.WHITE;
    private static final Color CINZA_TEXTO  = new Color(107, 114, 128);

    private JTextField     txtEmail;
    private JPasswordField txtSenha;
    private JLabel         lblErro;

    private final UsuarioService usuarioService;
    private final LoginController controller;

    public LoginAdmin(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.controller = new LoginController(usuarioService);

        setTitle("LauMiau - Login Administrativo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(480, 620);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(FUNDO);
        setLayout(new BorderLayout());
        add(criarConteudo(), BorderLayout.CENTER);
    }

    private JPanel criarConteudo() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(FUNDO);
        painel.setBorder(new EmptyBorder(40, 50, 40, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill      = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.weightx   = 1;


        JPanel iconeContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconeContainer.setOpaque(false);
        JPanel iconeBola = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(LARANJA_BASE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconeBola.setOpaque(false);
        iconeBola.setPreferredSize(new Dimension(90, 90));
        iconeBola.add(new JLabel("🔒", SwingConstants.CENTER), BorderLayout.CENTER);
        iconeContainer.add(iconeBola);
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 20, 0);
        painel.add(iconeContainer, gbc);


        JLabel lblTitulo = new JLabel("Área Administrativa", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(TEXTO_DARK);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 8, 0);
        painel.add(lblTitulo, gbc);


        JLabel lblSub = new JLabel(
                "<html><center>Entre com suas credenciais para acessar<br>o painel administrativo</center></html>",
                SwingConstants.CENTER);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSub.setForeground(CINZA_TEXTO);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 30, 0);
        painel.add(lblSub, gbc);


        txtEmail = criarCampoTexto("Usuário ou Email", false);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 15, 0);
        painel.add(criarCampoComIcone("👤", txtEmail), gbc);


        txtSenha = (JPasswordField) criarCampoTexto("Senha", true);
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 5, 0);
        painel.add(criarCampoSenhaComIcone(txtSenha), gbc);


        lblErro = new JLabel("", SwingConstants.CENTER);
        lblErro.setForeground(new Color(239, 68, 68));
        lblErro.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 10, 0);
        painel.add(lblErro, gbc);


        JButton btnEntrar = new JButton("Entrar") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, LARANJA_BASE, getWidth(), 0, LARANJA_DARK));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnEntrar.setForeground(BRANCO);
        btnEntrar.setPreferredSize(new Dimension(380, 50));
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEntrar.addActionListener(e -> fazerLogin());
        getRootPane().setDefaultButton(btnEntrar);
        gbc.gridy = 6; gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(btnEntrar, gbc);

        return painel;
    }


    private void fazerLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        LoginController.ResultadoLogin resultado = controller.autenticarAdmin(email, senha);

        switch (resultado) {
            case SUCESSO_ADMIN -> {
                lblErro.setText("");
                dispose();
                new RelatorioAdmin().setVisible(true);
            }
            case CREDENCIAIS_INVALIDAS -> lblErro.setText("Email ou senha inválidos.");
            case ERRO                  -> lblErro.setText("Erro ao conectar. Tente novamente.");
            default                    -> lblErro.setText("Acesso não permitido.");
        }
    }


    private JTextField criarCampoTexto(String placeholder, boolean senha) {
        JTextField campo = senha ? new JPasswordField() : new JTextField();
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setForeground(TEXTO_DARK);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        campo.setOpaque(false);
        if (!senha) {
            campo.setText(placeholder);
            campo.setForeground(CINZA_TEXTO);
            campo.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (campo.getText().equals(placeholder)) {
                        campo.setText(""); campo.setForeground(TEXTO_DARK);
                    }
                }
                public void focusLost(FocusEvent e) {
                    if (campo.getText().isEmpty()) {
                        campo.setText(placeholder); campo.setForeground(CINZA_TEXTO);
                    }
                }
            });
        }
        return campo;
    }

    private JPanel criarCampoComIcone(String icone, JTextField campo) {
        JPanel p = painelArredondado();
        p.add(labelIcone(icone), BorderLayout.WEST);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }

    private JPanel criarCampoSenhaComIcone(JPasswordField campo) {
        JPanel p = painelArredondado();
        campo.setEchoChar('•');
        JLabel btnToggle = new JLabel("🙈");
        btnToggle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnToggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnToggle.addMouseListener(new MouseAdapter() {
            boolean visivel = false;
            public void mouseClicked(MouseEvent e) {
                visivel = !visivel;
                campo.setEchoChar(visivel ? (char) 0 : '•');
                btnToggle.setText(visivel ? "👁" : "🙈");
            }
        });
        p.add(labelIcone("🔒"), BorderLayout.WEST);
        p.add(campo, BorderLayout.CENTER);
        p.add(btnToggle, BorderLayout.EAST);
        return p;
    }

    private JPanel painelArredondado() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(BORDAS_LEVES); g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(380, 50));
        p.setBorder(new EmptyBorder(0, 15, 0, 15));
        return p;
    }

    private JLabel labelIcone(String icone) {
        JLabel lbl = new JLabel(icone);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lbl.setBorder(new EmptyBorder(0, 0, 0, 8));
        return lbl;
    }
}