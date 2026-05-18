package br.com.laumiau.view;

import jakarta.persistence.EntityManager;
import laumiau.infra.JPAUtil;
import laumiau.model.Usuario;
import laumiau.repository.AnimalRepository;
import laumiau.repository.UsuarioRepository;
import laumiau.service.AnimalService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginView extends JFrame {

    private static final int CARD_LARGURA = 500;
    private static final int CARD_ALTURA = 620;

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JLabel imagemCard;

    private EntityManager em;
    private UsuarioRepository usuarioRepository;

    public LoginView() {
        setTitle("Login - LauMiau");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        JPanel painelPrincipal = new JPanel(null);
        painelPrincipal.setPreferredSize(new Dimension(CARD_LARGURA, CARD_ALTURA));
        painelPrincipal.setBackground(AppTheme.FUNDO); // Usando o tema
        setContentPane(painelPrincipal);

        pack();
        setLocationRelativeTo(null);

        carregarImagemCard();
        criarCampos();
    }

    private UsuarioRepository getUsuarioRepository() {
        if (usuarioRepository == null) {
            em = JPAUtil.getEntityManager();
            usuarioRepository = new UsuarioRepository(em);
        }
        return usuarioRepository;
    }

    private void carregarImagemCard() {
        URL url = ClassLoader.getSystemResource("teste-login.png");
        if (url == null) {
            JOptionPane.showMessageDialog(this, "Imagem teste-login.png não encontrada.");
            return;
        }
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(CARD_LARGURA, CARD_ALTURA, Image.SCALE_SMOOTH);
        imagemCard = new JLabel(new ImageIcon(img));


        imagemCard.setBounds(0, 0, CARD_LARGURA, CARD_ALTURA);
        add(imagemCard);
    }

    private void criarCampos() {

        txtEmail = new JTextField();
        txtEmail.setBounds(125, 325, 290, 45);
        configurarCampo(txtEmail);
        add(txtEmail);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(125, 407, 290, 45);
        configurarCampo(txtSenha);
        add(txtSenha);

        btnEntrar = new JButton("");
        btnEntrar.setBounds(80, 460, 340, 45);
        configurarBotao(btnEntrar);
        add(btnEntrar);

        JButton btnAdmin = new JButton("");
        btnAdmin.setBounds(135, 535, 140, 30);
        configurarBotao(btnAdmin);
        add(btnAdmin);

        JButton btnCadastrar = new JButton("");
        btnCadastrar.setBounds(280, 550, 150, 25);
        configurarBotao(btnCadastrar);
        add(btnCadastrar);

        btnEntrar.addActionListener(e -> fazerLoginUsuario());

        btnCadastrar.addActionListener(e -> {
            new CadastroView().setVisible(true);
            dispose();
        });

        btnAdmin.addActionListener(e -> {
            new LoginAdmin().setVisible(true);
            dispose();
        });

        if (imagemCard != null) {
            getContentPane().setComponentZOrder(imagemCard, getContentPane().getComponentCount() - 1);
        }
        repaint();
    }

    private void fazerLoginUsuario() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o e-mail e a senha.");
            return;
        }

        try {
            Usuario usuario = getUsuarioRepository().buscarPorEmail(email);

            if (usuario == null || !usuario.autenticar(senha)) {
                JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos.");
                return;
            }

            if (usuario.getTipo() == laumiau.model.TipoUsuario.admin) {
                JOptionPane.showMessageDialog(this,
                        "Administradores devem usar o botão 'Admin' para entrar no sistema.",
                        "Acesso Restrito",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            EntityManager emAnimal = JPAUtil.getEntityManager();
            AnimalService animalService = new AnimalService(new AnimalRepository(emAnimal));

            new AnimalView(animalService).setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao fazer login: " + ex.getMessage());
        }
    }

    private void configurarCampo(JTextField campo) {
        campo.setBorder(null);
        campo.setOpaque(false);
        campo.setForeground(AppTheme.TEXTO_DARK); // Usando o tema
        campo.setCaretColor(AppTheme.TEXTO_DARK);
        campo.setFont(AppTheme.FONTE_TEXTO);      // Usando o tema
    }

    private void configurarBotao(JButton botao) {
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}