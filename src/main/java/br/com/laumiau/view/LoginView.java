package br.com.laumiau.view;

import laumiau.controller.LoginController;
import laumiau.infra.JPAUtil;
import laumiau.model.Cliente;
import laumiau.repository.AnimalRepository;
import laumiau.service.AnimalService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginView extends JFrame {

    private static final int CARD_LARGURA = 500;
    private static final int CARD_ALTURA  = 620;

    private JTextField     txtEmail;
    private JPasswordField txtSenha;
    private JButton        btnEntrar;
    private JLabel         imagemCard;


    private final LoginController controller = new LoginController();

    public LoginView() {
        setTitle("Login - LauMiau");
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

        btnEntrar.addActionListener(e -> fazerLogin());
        btnCadastrar.addActionListener(e -> {
            new CadastroView().setVisible(true);
            dispose();
        });
        btnAdmin.addActionListener(e -> {
            new LoginAdmin().setVisible(true);
            dispose();
        });

        if (imagemCard != null) {
            getContentPane().setComponentZOrder(
                    imagemCard, getContentPane().getComponentCount() - 1);
        }
        repaint();
    }


    private void fazerLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        LoginController.ResultadoLogin resultado = controller.autenticarCliente(email, senha);

        switch (resultado) {
            case SUCESSO_CLIENTE -> {
                Cliente clienteLogado = controller.getClienteLogado();
                AnimalService animalService = new AnimalService(
                        new AnimalRepository(JPAUtil.getEntityManager())
                );
                new AnimalView(animalService, clienteLogado).setVisible(true);
                dispose();
            }
            case ADMIN_NA_TELA_ERRADA -> JOptionPane.showMessageDialog(this,
                    "Administradores devem usar o botão 'Admin' para entrar.",
                    "Acesso Restrito", JOptionPane.WARNING_MESSAGE);
            case CREDENCIAIS_INVALIDAS -> JOptionPane.showMessageDialog(this,
                    "E-mail ou senha inválidos.");
            case USUARIO_NAO_CLIENTE -> JOptionPane.showMessageDialog(this,
                    "Tipo de usuário não reconhecido.");
            case ERRO -> JOptionPane.showMessageDialog(this,
                    "Erro ao conectar. Tente novamente.");
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