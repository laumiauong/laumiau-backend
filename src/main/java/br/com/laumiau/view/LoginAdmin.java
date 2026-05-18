package br.com.laumiau.view;

import laumiau.infra.JPAUtil;
import laumiau.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginAdmin extends JFrame {

    private static final Color LARANJA_BASE  = new Color(255, 153, 0);
    private static final Color LARANJA_DARK  = new Color(249, 115, 22);
    private static final Color FUNDO         = new Color(255, 251, 245);
    private static final Color TEXTO_DARK    = new Color(31, 41, 55);
    private static final Color BORDAS_LEVES  = new Color(230, 230, 230);
    private static final Color BRANCO        = Color.WHITE;
    private static final Color CINZA_TEXTO   = new Color(107, 114, 128);

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JLabel lblErro;

    public LoginAdmin() {
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.weightx = 1;

        // Ícone de cadeado
        JPanel iconeContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconeContainer.setOpaque(false);
        // Painel redondo para o ícone
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
        JLabel lblIcone = new JLabel("🔒", SwingConstants.CENTER);
        lblIcone.setFont(new Font("SansSerif", Font.PLAIN, 36));
        iconeBola.add(lblIcone, BorderLayout.CENTER);
        iconeContainer.add(iconeBola);

        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 20, 0);
        painel.add(iconeContainer, gbc);

        // Título
        JLabel lblTitulo = new JLabel("Área Administrativa", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(TEXTO_DARK);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 8, 0);
        painel.add(lblTitulo, gbc);

        // Subtítulo
        JLabel lblSub = new JLabel("<html><center>Entre com suas credenciais para acessar<br>o painel administrativo</center></html>", SwingConstants.CENTER);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSub.setForeground(CINZA_TEXTO);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 30, 0);
        painel.add(lblSub, gbc);

        // Campo Email
        txtEmail = criarCampoTexto("Usuário ou Email", false);
        JPanel painelEmail = criarCampoComIcone("👤", txtEmail);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 15, 0);
        painel.add(painelEmail, gbc);

        // Campo Senha
        txtSenha = (JPasswordField) criarCampoTexto("Senha", true);
        JPanel painelSenha = criarCampoSenhaComIcone(txtSenha);
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 5, 0);
        painel.add(painelSenha, gbc);

        // Esqueceu senha
        JPanel painelEsqueceu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        painelEsqueceu.setOpaque(false);
        JLabel lblEsqueceu = new JLabel("esqueceu sua senha?");
        lblEsqueceu.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblEsqueceu.setForeground(CINZA_TEXTO);
        lblEsqueceu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        painelEsqueceu.add(lblEsqueceu);
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 20, 0);
        painel.add(painelEsqueceu, gbc);

        // Label de erro
        lblErro = new JLabel("", SwingConstants.CENTER);
        lblErro.setForeground(new Color(239, 68, 68));
        lblErro.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridy = 6; gbc.insets = new Insets(0, 0, 10, 0);
        painel.add(lblErro, gbc);

        // Botão Entrar com Degradê
        JButton btnEntrar = new JButton("Entrar") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint grad = new GradientPaint(0, 0, LARANJA_BASE, getWidth(), 0, LARANJA_DARK);
                g2.setPaint(grad);
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

        // ✅ FLUXO CORRIGIDO: O ActionListener chama o método que faz o dispose()
        btnEntrar.addActionListener(e -> fazerLogin());

        // Enter também faz login
        getRootPane().setDefaultButton(btnEntrar);

        gbc.gridy = 7; gbc.insets = new Insets(0, 0, 25, 0);
        painel.add(btnEntrar, gbc);

        // Rodapé
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        rodape.setOpaque(false);
        JLabel pol = new JLabel("Política de Privacidade");
        pol.setFont(new Font("SansSerif", Font.PLAIN, 11));
        pol.setForeground(CINZA_TEXTO);
        JLabel patinha = new JLabel("🐾");
        JLabel termos = new JLabel("Termos de Serviço");
        termos.setFont(new Font("SansSerif", Font.PLAIN, 11));
        termos.setForeground(CINZA_TEXTO);
        rodape.add(pol);
        rodape.add(patinha);
        rodape.add(termos);
        gbc.gridy = 8; gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(rodape, gbc);

        return painel;
    }

    private JTextField criarCampoTexto(String placeholder, boolean senha) {
        JTextField campo = senha ? new JPasswordField() : new JTextField();
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setForeground(TEXTO_DARK);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        campo.setOpaque(false);

        // Placeholder
        if (!senha) {
            campo.setText(placeholder);
            campo.setForeground(CINZA_TEXTO);
            campo.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (campo.getText().equals(placeholder)) {
                        campo.setText("");
                        campo.setForeground(TEXTO_DARK);
                    }
                }
                public void focusLost(FocusEvent e) {
                    if (campo.getText().isEmpty()) {
                        campo.setText(placeholder);
                        campo.setForeground(CINZA_TEXTO);
                    }
                }
            });
        }
        return campo;
    }

    private JPanel criarCampoComIcone(String icone, JTextField campo) {
        JPanel painel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(BORDAS_LEVES);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
                g2.dispose();
            }
        };
        painel.setOpaque(false);
        painel.setPreferredSize(new Dimension(380, 50));
        painel.setBorder(new EmptyBorder(0, 15, 0, 15));

        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblIcone.setBorder(new EmptyBorder(0, 0, 0, 8));
        painel.add(lblIcone, BorderLayout.WEST);
        painel.add(campo, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarCampoSenhaComIcone(JPasswordField campo) {
        JPanel painel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(BORDAS_LEVES);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
                g2.dispose();
            }
        };
        painel.setOpaque(false);
        painel.setPreferredSize(new Dimension(380, 50));
        painel.setBorder(new EmptyBorder(0, 15, 0, 15));

        JLabel lblIcone = new JLabel("🔒");
        lblIcone.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblIcone.setBorder(new EmptyBorder(0, 0, 0, 8));

        // Botão mostrar/ocultar senha original restaurado
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

        campo.setEchoChar('•');

        painel.add(lblIcone, BorderLayout.WEST);
        painel.add(campo, BorderLayout.CENTER);
        painel.add(btnToggle, BorderLayout.EAST);
        return painel;
    }

    private void fazerLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (email.isEmpty() || email.equals("Usuário ou Email") || senha.isEmpty()) {
            lblErro.setText("Preencha todos os campos.");
            return;
        }

        try {
            EntityManager em = JPAUtil.getEntityManager();
            Usuario usuario = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha AND u.tipo = :tipo",
                            Usuario.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .setParameter("tipo", laumiau.model.TipoUsuario.admin)
                    .getSingleResult();

            em.close();
            lblErro.setText("");

            // ✅ FLUXO CORRIGIDO: Fecha esta janela e abre o painel Admin
            dispose();
            new RelatorioAdmin().setVisible(true);

        } catch (NoResultException e) {
            lblErro.setText("Email ou senha inválidos.");
        } catch (Exception e) {
            lblErro.setText("Erro ao conectar. Tente novamente.");
            e.printStackTrace();
        }
    }
}