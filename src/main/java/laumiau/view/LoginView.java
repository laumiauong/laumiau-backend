package laumiau.view;

import jakarta.persistence.EntityManager;
import laumiau.infra.JPAUtil;
import laumiau.model.Admin;
import laumiau.model.Usuario;
import laumiau.repository.UsuarioRepository;

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

    private int cardX;
    private int cardY;

    private EntityManager em;
    private UsuarioRepository usuarioRepository;

    public LoginView() {

        setTitle("Login - LauMiau");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        getContentPane().setBackground(new Color(242, 242, 242));

        calcularPosicaoCard();
        carregarImagemCard();
        criarCampos();

        setVisible(true);
    }

    private UsuarioRepository getUsuarioRepository() {
        if (usuarioRepository == null) {
            em = JPAUtil.getEntityManager();
            usuarioRepository = new UsuarioRepository(em);
        }

        return usuarioRepository;
    }

    private void calcularPosicaoCard() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

        cardX = (tela.width - CARD_LARGURA) / 2;
        cardY = (tela.height - CARD_ALTURA) / 2;
    }

    private void carregarImagemCard() {

        URL url = ClassLoader.getSystemResource("teste-login.png");

        if (url == null) {
            JOptionPane.showMessageDialog(this, "Imagem teste-login.png não encontrada.");
            return;
        }

        ImageIcon icon = new ImageIcon(url);

        Image img = icon.getImage().getScaledInstance(
                CARD_LARGURA,
                CARD_ALTURA,
                Image.SCALE_SMOOTH
        );

        imagemCard = new JLabel(new ImageIcon(img));
        imagemCard.setBounds(cardX, cardY, CARD_LARGURA, CARD_ALTURA);

        add(imagemCard);
    }

    private void criarCampos() {

        txtEmail = new JTextField();
        txtEmail.setBounds(cardX + 125, cardY + 325, 290, 45);
        configurarCampo(txtEmail);
        add(txtEmail);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(cardX + 125, cardY + 407, 290, 45);
        configurarCampo(txtSenha);
        add(txtSenha);

        btnEntrar = new JButton("");
        btnEntrar.setBounds(cardX + 80, cardY + 460, 340, 45);
        configurarBotao(btnEntrar);
        add(btnEntrar);

        JButton btnAdmin = new JButton("");
        btnAdmin.setBounds(cardX + 135, cardY + 535, 140, 30);
        configurarBotao(btnAdmin);
        add(btnAdmin);

        JButton btnCadastrar = new JButton("");
        btnCadastrar.setBounds(cardX + 280, cardY + 550, 150, 25);
        configurarBotao(btnCadastrar);
        add(btnCadastrar);

        btnEntrar.addActionListener(e -> fazerLoginUsuario());

        btnCadastrar.addActionListener(e -> {
            new CadastroView();
            dispose();
        });

        btnAdmin.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "O acesso administrativo é feito pelo Main.java.\n" +
                            "No momento sta tela é destinada ao login e cadastro de usuários."
            );
        });

        if (imagemCard != null) {
            getContentPane().setComponentZOrder(
                    imagemCard,
                    getContentPane().getComponentCount() - 1
            );
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

            JOptionPane.showMessageDialog(
                    this,
                    "Login realizado com sucesso!\nBem-vindo, " + usuario.getNome()
            );

            /*
             * Quando tiver uma tela principal do usuário, abra aqui.
             * Exemplo:
             *
             * new TelaPrincipalUsuarioView();
             * dispose();
             */

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao fazer login: " + ex.getMessage()
            );
        }
    }

    private void fazerLoginAdmin() {

        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o e-mail e a senha para login de administrador.");
            return;
        }

        try {
            Usuario usuario = getUsuarioRepository().buscarPorEmail(email);

            if (usuario == null || !usuario.autenticar(senha)) {
                JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos.");
                return;
            }

            if (!(usuario instanceof Admin)) {
                JOptionPane.showMessageDialog(this, "Este usuário não possui permissão de administrador.");
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Login de administrador realizado com sucesso!\nBem-vindo, " + usuario.getNome()
            );

            /*
             * A parte do admin ainda está no Main pelo console.
             * Se depois criarem uma tela de admin, abra ela aqui.
             */

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao fazer login de administrador: " + ex.getMessage()
            );
        }
    }

    private void configurarCampo(JTextField campo) {
        campo.setBorder(null);
        campo.setOpaque(false);
        campo.setForeground(Color.BLACK);
        campo.setCaretColor(Color.BLACK);
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    private void configurarBotao(JButton botao) {
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}