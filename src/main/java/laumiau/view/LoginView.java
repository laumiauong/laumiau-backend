package laumiau.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JLabel imagemCard;

    public LoginView() {

        setTitle("Login - LauMiau");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        getContentPane().setBackground(new Color(242, 242, 242));

        carregarImagemCard();
        criarCampos();

        setVisible(true);
    }

    private void carregarImagemCard() {

        ImageIcon icon = new ImageIcon(
                ClassLoader.getSystemResource("design-tela-login.png")
        );

        Image img = icon.getImage().getScaledInstance(
                500,
                620,
                Image.SCALE_SMOOTH
        );

        imagemCard = new JLabel(new ImageIcon(img));

        imagemCard.setBounds(430, 80, 500, 620);

        add(imagemCard);
    }

    private void criarCampos() {
        int cardX = 430;
        int cardY = 80;

        txtEmail = new JTextField();
        txtEmail.setBounds(cardX + 130, cardY + 323, 285, 45);
        txtEmail.setBorder(null);
        txtEmail.setOpaque(false);
        txtEmail.setForeground(Color.BLACK);
        txtEmail.setCaretColor(Color.BLACK);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        add(txtEmail);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(cardX + 130, cardY + 405, 285, 45);
        txtSenha.setBorder(null);
        txtSenha.setOpaque(false);
        txtSenha.setForeground(Color.BLACK);
        txtSenha.setCaretColor(Color.BLACK);
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 16));
        add(txtSenha);

        btnEntrar = new JButton("");
        btnEntrar.setBounds(cardX + 80, cardY + 460, 340, 45);
        btnEntrar.setOpaque(false);
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnEntrar);

        JButton btnAdmin = new JButton("");
        btnAdmin.setBounds(cardX + 135, cardY + 535, 230, 30);
        btnAdmin.setOpaque(false);
        btnAdmin.setContentAreaFilled(false);
        btnAdmin.setBorderPainted(false);
        btnAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnAdmin);

        JButton btnCadastrar = new JButton("");
        btnCadastrar.setBounds(cardX + 270, cardY + 575, 110, 25);
        btnCadastrar.setOpaque(false);
        btnCadastrar.setContentAreaFilled(false);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnCadastrar);

        btnEntrar.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());

            JOptionPane.showMessageDialog(this,
                    "Email: " + email + "\nSenha: " + senha);
        });

        btnAdmin.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Login do administrador");
        });

        btnCadastrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Tela de cadastro");
        });

        getContentPane().setComponentZOrder(imagemCard, getContentPane().getComponentCount() - 1);

        repaint();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(LoginView::new);
    }
}