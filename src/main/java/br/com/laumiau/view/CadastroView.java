package br.com.laumiau.view;

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

    private EntityManager em;
    private UsuarioRepository usuarioRepository;

    public CadastroView() {
        setTitle("Cadastro - LauMiau");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Painel com tamanho exato para não desalinhar a imagem
        JPanel painelPrincipal = new JPanel(null);
        painelPrincipal.setPreferredSize(new Dimension(CARD_LARGURA, CARD_ALTURA));
        painelPrincipal.setBackground(AppTheme.FUNDO);
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
        URL url = ClassLoader.getSystemResource("teste-cadastro.png");
        if (url == null) {
            JOptionPane.showMessageDialog(this, "Imagem teste-cadastro.png não encontrada.");
            return;
        }
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(CARD_LARGURA, CARD_ALTURA, Image.SCALE_SMOOTH);
        imagemCard = new JLabel(new ImageIcon(img));

        imagemCard.setBounds(0, 0, CARD_LARGURA, CARD_ALTURA);
        add(imagemCard);
    }

    private void criarCampos() {
        // Posições originais ajustadas para a posição (0,0) da janela
        txtNome = new JTextField();
        txtNome.setBounds(110, 270, 275, 35);
        configurarCampo(txtNome);
        add(txtNome);

        txtEmail = new JTextField();
        txtEmail.setBounds(110, 341, 275, 35);
        configurarCampo(txtEmail);
        add(txtEmail);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(110, 409, 275, 35);
        configurarCampo(txtSenha);
        add(txtSenha);

        txtConfirmarSenha = new JPasswordField();
        txtConfirmarSenha.setBounds(110, 478, 275, 35);
        configurarCampo(txtConfirmarSenha);
        add(txtConfirmarSenha);

        btnCadastrar = new JButton("");
        btnCadastrar.setBounds(83, 517, 333, 35);
        configurarBotao(btnCadastrar);
        add(btnCadastrar);

        btnEntrar = new JButton("");
        btnEntrar.setBounds(285, 575, 85, 30);
        configurarBotao(btnEntrar);
        add(btnEntrar);

        // ✅ FLUXO CORRIGIDO
        btnCadastrar.addActionListener(e -> cadastrarUsuario());

        btnEntrar.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        if (imagemCard != null) {
            getContentPane().setComponentZOrder(imagemCard, getContentPane().getComponentCount() - 1);
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

            // ✅ FLUXO CORRIGIDO
            new LoginView().setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário: " + ex.getMessage());
        }
    }

    private void configurarCampo(JTextField campo) {
        campo.setBorder(null);
        campo.setOpaque(false);
        campo.setForeground(AppTheme.TEXTO_DARK);
        campo.setCaretColor(AppTheme.TEXTO_DARK);
        campo.setFont(AppTheme.FONTE_TEXTO);
    }

    private void configurarBotao(JButton botao) {
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}