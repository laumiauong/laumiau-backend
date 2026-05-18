package br.com.laumiau.view;

import javax.swing.*;
import java.awt.*;

public class FormularioContatoFrame extends JFrame {
    
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextArea txtMensagem;
    
    public FormularioContatoFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lau Miau - Entre em Contato");
        setSize(600, 650);
        getContentPane().setBackground(new Color(255, 245, 240));
        setLayout(null);
        

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(50, 30, 500, 560);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(235, 235, 235), 1),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
));        
        // Título
        JLabel tituloLabel = new JLabel("Entre em Contato");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 28));
        tituloLabel.setForeground(new Color(255, 107, 38));
        tituloLabel.setBounds(30, 30, 300, 40);
        mainPanel.add(tituloLabel);
        
        // Subtítulo
        JLabel subtituloLabel = new JLabel("Preencha o formulário e entraremos em contato!");
        subtituloLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtituloLabel.setForeground(Color.GRAY);
        subtituloLabel.setBounds(30, 70, 400, 20);
        mainPanel.add(subtituloLabel);
        

        JLabel lblNome = new JLabel("Nome completo *");
        lblNome.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNome.setBounds(30, 120, 200, 20);
        mainPanel.add(lblNome);
        
        txtNome = new JTextField();
        txtNome.setBounds(30, 145, 440, 35);
        txtNome.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNome.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(txtNome);
        

        JLabel lblEmail = new JLabel("E-mail *");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEmail.setBounds(30, 195, 200, 20);
        mainPanel.add(lblEmail);
        
        txtEmail = new JTextField();
        txtEmail.setBounds(30, 220, 440, 35);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(txtEmail);
        

        JLabel lblTelefone = new JLabel("Telefone *");
        lblTelefone.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTelefone.setBounds(30, 270, 200, 20);
        mainPanel.add(lblTelefone);
        
        txtTelefone = new JTextField();
        txtTelefone.setBounds(30, 295, 440, 35);
        txtTelefone.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTelefone.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(txtTelefone);
        

        JLabel lblMensagem = new JLabel("Mensagem");
        lblMensagem.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMensagem.setBounds(30, 345, 200, 20);
        mainPanel.add(lblMensagem);
        
        txtMensagem = new JTextArea();
        txtMensagem.setFont(new Font("Arial", Font.PLAIN, 14));
        txtMensagem.setLineWrap(true);
        txtMensagem.setWrapStyleWord(true);
        
        JScrollPane scrollMensagem = new JScrollPane(txtMensagem);
        scrollMensagem.setBounds(30, 370, 440, 100);
        scrollMensagem.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        mainPanel.add(scrollMensagem);
        

        JButton btnEnviar = new JButton("Enviar Mensagem");
        btnEnviar.setBounds(30, 490, 440, 45);
        btnEnviar.setBackground(new Color(255, 107, 38));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEnviar.setBorder(BorderFactory.createEmptyBorder());
        btnEnviar.addMouseListener(new java.awt.event.MouseAdapter() {
            
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btnEnviar.setBackground(new Color(235, 97, 28));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btnEnviar.setBackground(new Color(255, 122, 48));
        }
    });
        btnEnviar.addActionListener(e -> enviarFormulario());
        
        mainPanel.add(btnEnviar);
        
        add(mainPanel);
    }
    
    private void enviarFormulario() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String mensagem = txtMensagem.getText().trim();
        
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, preencha todos os campos obrigatórios!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        

        JOptionPane.showMessageDialog(this,
            "Mensagem enviada com sucesso!\nEntraremos em contato em breve.",
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);
        

        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtMensagem.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormularioContatoFrame().setVisible(true);
        });
    }
}