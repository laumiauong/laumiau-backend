package laumiau.view;

import jakarta.persistence.EntityManager;
import laumiau.infra.JPAUtil;
import laumiau.model.Cliente;
import laumiau.model.Usuario;
import laumiau.repository.UsuarioRepository;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CadastroView extends JFrame {

    private static final int CARD_LARGURA = 500;
    private static final int CARD_ALTURA = 620;

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;

    private JButton btnCadastrar;
    private JButton btnEntrar;

    private JLabel imagemCard;

    private int cardX;
    private int cardY;

    private EntityManager em;
    private UsuarioRepository usuarioRepository;

    public CadastroView() {

        setTitle("Cadastro - LauMiau");
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

        URL url = ClassLoader.getSystemResource("teste-cadastro.png");

        if (url == null) {
            JOptionPane.showMessageDialog(this, "Imagem teste-cadastro.png não encontrada.");
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

        txtNome = new JTextField();
        txtNome.setBounds(cardX + 110, cardY + 270, 275, 35);
        configurarCampo(txtNome);
        add(txtNome);

        txtEmail = new JTextField();
        txtEmail.setBounds(cardX + 110, cardY + 341, 275, 35);
        configurarCampo(txtEmail);
        add(txtEmail);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(cardX + 110, cardY + 409, 275, 35);
        configurarCampo(txtSenha);
        add(txtSenha);

        txtConfirmarSenha = new JPasswordField();
        txtConfirmarSenha.setBounds(cardX + 110, cardY + 478, 275, 35);
        configurarCampo(txtConfirmarSenha);
        add(txtConfirmarSenha);

        btnCadastrar = new JButton("");
        btnCadastrar.setBounds(cardX + 83, cardY + 517, 333, 35);
        configurarBotao(btnCadastrar);
        add(btnCadastrar);

        btnEntrar = new JButton("");
        btnEntrar.setBounds(cardX + 285, cardY + 575, 85, 30);
        configurarBotao(btnEntrar);
        add(btnEntrar);

        btnCadastrar.addActionListener(e -> cadastrarUsuario());

        btnEntrar.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        if (imagemCard != null) {
            getContentPane().setComponentZOrder(
                    imagemCard,
                    getContentPane().getComponentCount() - 1
            );
        }

        repaint();
    }

    private void cadastrarUsuario() {

        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        String confirmarSenha = new String(txtConfirmarSenha.getPassword()).trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Informe um e-mail válido.");
            return;
        }

        if (senha.length() < 4) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 4 caracteres.");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.");
            return;
        }

        try {
            Usuario usuarioExistente = getUsuarioRepository().buscarPorEmail(email);

            if (usuarioExistente != null) {
                JOptionPane.showMessageDialog(this, "Já existe um usuário cadastrado com este e-mail.");
                return;
            }

            Cliente cliente = new Cliente(null, nome, email, senha);

            getUsuarioRepository().salvar(cliente);

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");

            new LoginView();
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao cadastrar usuário: " + ex.getMessage()
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
        SwingUtilities.invokeLater(CadastroView::new);
    }
}