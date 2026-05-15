package laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TutorView extends JFrame {

    private JTextField campoNome;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JPasswordField campoSenha;

    private JButton btnCadastrar;
    private JButton btnVoltar;

    public TutorView() {
        setTitle("Lau Miau - Cadastro Tutor");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        criarTela();
    }

    private void criarTela() {
        JPanel fundo = new JPanel(new BorderLayout());
        fundo.setBackground(new Color(255, 250, 243));
        fundo.setBorder(new EmptyBorder(30, 60, 30, 60));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);

        btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 22));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> dispose());

        JLabel logo = new JLabel("LAU 🐾 MIAU", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 30));
        logo.setForeground(new Color(255, 102, 0));

        topo.add(btnVoltar, BorderLayout.WEST);
        topo.add(logo, BorderLayout.CENTER);

        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Crie sua Conta");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(20, 30, 50));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 35, 30, 35));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(430, 430));

        campoNome = criarCampo("Nome completo");
        campoEmail = criarCampo("Email");
        campoTelefone = criarCampo("Telefone");
        campoSenha = criarCampoSenha("Crie uma senha");

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setMaximumSize(new Dimension(360, 55));
        btnCadastrar.setBackground(new Color(255, 102, 0));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 17));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(e -> cadastrarTutor());

        card.add(campoNome);
        card.add(Box.createVerticalStrut(18));
        card.add(campoEmail);
        card.add(Box.createVerticalStrut(18));
        card.add(campoTelefone);
        card.add(Box.createVerticalStrut(18));
        card.add(campoSenha);
        card.add(Box.createVerticalStrut(30));
        card.add(btnCadastrar);

        centro.add(Box.createVerticalStrut(20));
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(25));
        centro.add(card);

        fundo.add(topo, BorderLayout.NORTH);
        fundo.add(centro, BorderLayout.CENTER);

        add(fundo);
    }

    private JTextField criarCampo(String placeholder) {
        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(360, 50));
        campo.setFont(new Font("Arial", Font.PLAIN, 15));
        campo.setForeground(new Color(20, 30, 50));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1),
                new EmptyBorder(0, 18, 0, 18)
        ));
        campo.setText(placeholder);
        campo.setForeground(new Color(150, 150, 150));

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(new Color(20, 30, 50));
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(new Color(150, 150, 150));
                }
            }
        });

        return campo;
    }

    private JPasswordField criarCampoSenha(String placeholder) {
        JPasswordField campo = new JPasswordField();
        campo.setMaximumSize(new Dimension(360, 50));
        campo.setFont(new Font("Arial", Font.PLAIN, 15));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1),
                new EmptyBorder(0, 18, 0, 18)
        ));
        campo.setText(placeholder);
        campo.setForeground(new Color(150, 150, 150));
        campo.setEchoChar((char) 0);

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(campo.getPassword()).equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(new Color(20, 30, 50));
                    campo.setEchoChar('●');
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (String.valueOf(campo.getPassword()).isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(new Color(150, 150, 150));
                    campo.setEchoChar((char) 0);
                }
            }
        });

        return campo;
    }

    private void cadastrarTutor() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String telefone = campoTelefone.getText();
        String senha = String.valueOf(campoSenha.getPassword());

        if (nome.equals("Nome completo") || email.equals("Email")
                || telefone.equals("Telefone") || senha.equals("Crie uma senha")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Tutor cadastrado com sucesso!\n\n"
                + "Nome: " + nome + "\n"
                + "Email: " + email + "\n"
                + "Telefone: " + telefone,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TutorView().setVisible(true);
        });
    }
}