package br.com.laumiau.view;

import laumiau.controller.CadastroController;
import laumiau.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CadastroView extends JFrame {

    private static final int CARD_LARGURA = 500;
    private static final int CARD_ALTURA  = 620;

    private JTextField     txtNome;
    private JTextField     txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    private JButton        btnCadastrar;
    private JButton        btnEntrar;
    private JLabel         imagemCard;

    private final UsuarioService usuarioService;
    private final CadastroController controller;

    public CadastroView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.controller = new CadastroController(usuarioService);

        setTitle("Cadastro - LauMiau");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(null);
        painelPrincipal.setPreferredSize(new Dimension(CARD_LARGURA, CARD_ALTURA));
        painelPrincipal.setBackground(AppTheme.FUNDO);
        setContentPane(painelPrincipal);

        pack();
        setLocationRelativeTo(null);

        carregarImagemCard();
        criarCampos();
    }

    private void carregarImagemCard() {
        URL url = getClass().getClassLoader().getResource("teste-cadastro.png");
        if (url == null) {
            System.err.println("Aviso: Imagem teste-cadastro.png não encontrada.");
            return;
        }
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(CARD_LARGURA, CARD_ALTURA, Image.SCALE_SMOOTH);
        imagemCard = new JLabel(new ImageIcon(img));
        imagemCard.setBounds(0, 0, CARD_LARGURA, CARD_ALTURA);
        add(imagemCard);
    }

    private void criarCampos() {
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

        btnCadastrar.addActionListener(e -> cadastrar());
        btnEntrar.addActionListener(e -> {
            new LoginView(usuarioService).setVisible(true);
            dispose();
        });

        if (imagemCard != null) {
            getContentPane().setComponentZOrder(
                    imagemCard, getContentPane().getComponentCount() - 1);
        }
        repaint();
    }

    private void cadastrar() {
        String nome            = txtNome.getText().trim();
        String email           = txtEmail.getText().trim();
        String senha           = new String(txtSenha.getPassword()).trim();
        String confirmarSenha  = new String(txtConfirmarSenha.getPassword()).trim();

        String erro = controller.cadastrar(nome, email, senha, confirmarSenha);

        if (erro != null) {
            JOptionPane.showMessageDialog(this, erro, "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            new LoginView(usuarioService).setVisible(true);
            dispose();
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