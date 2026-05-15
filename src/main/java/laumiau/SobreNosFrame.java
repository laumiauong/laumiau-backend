package laumiau; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SobreNosFrame extends JFrame {
    
    public SobreNosFrame() {
        initComponents();
        setSize(1200, 700);
        setLocationRelativeTo(null); // Centraliza a janela
    }
    
    private void initComponents() {
        // Configurações da janela
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lau Miau - Sobre Nós");
        setSize(1200, 700);
        getContentPane().setBackground(new Color(255, 248, 240));
        setLayout(null);
        
        // Painel do Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1200, 80);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(null);
        
        // Logo
        JLabel logoLabel = new JLabel("LAU🐾MIAU");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 28));
        logoLabel.setForeground(new Color(255, 107, 38));
        logoLabel.setBounds(50, 20, 200, 40);
        headerPanel.add(logoLabel);
        
        // Menu
        JLabel menuHome = createMenuLabel("Home", 570, 30);
        JLabel menuAnimais = createMenuLabel("Animais", 660, 30);
        JLabel menuSobre = createMenuLabel("Sobre nós", 780, 30);
        menuSobre.setForeground(new Color(255, 107, 38));
        menuSobre.setBackground(new Color(255, 220, 190));
        menuSobre.setOpaque(true);
        
        JLabel menuAdmin = new JLabel("👤 Admin");
        menuAdmin.setFont(new Font("Arial", Font.PLAIN, 16));
        menuAdmin.setBounds(1050, 30, 100, 25);
        menuAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        headerPanel.add(menuHome);
        headerPanel.add(menuAnimais);
        headerPanel.add(menuSobre);
        headerPanel.add(menuAdmin);
        
        add(headerPanel);
        
        // Painel de conteúdo
        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(150, 150, 900, 450);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(null);
        
        // Título
        JLabel tituloLabel = new JLabel("Sobre nós!");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 36));
        tituloLabel.setBounds(50, 30, 300, 50);
        contentPanel.add(tituloLabel);
        
        // Texto descritivo
        JTextArea textoArea = new JTextArea();
        textoArea.setText("O Lau Miau nasceu com a missão de aproximar animais incríveis\n" +
                         "de lares cheios de amor, agora, por meio de uma plataforma\n" +
                         "moderna e interativa! Aqui, quem deseja adotar encontra um\n" +
                         "guia fácil e divertido de localizar cães e gatos que estão prontos\n" +
                         "para dar e receber carinho.\n\n" +
                         "À frente do projeto está a Dra. Lavanda Lara, médica\n" +
                         "veterinária e pet sitter, dedicada ao bem-estar animal. Com\n" +
                         "ampla experiência e amor pela causa, ela também oferece\n" +
                         "serviços profissionais de atendimento veterinário e cuidados\n" +
                         "personalizados para pets. Ideais para quem busca confiança e\n" +
                         "carinho nos momentos em que não pode estar presente.\n\n" +
                         "Para saber mais sobre os serviços veterinários ou de pet sitter,\n" +
                         "basta entrar em contato. O cuidado com os animais é levado a\n" +
                         "sério – sempre com responsabilidade, afeto e profissionalismo.");
        textoArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textoArea.setLineWrap(true);
        textoArea.setWrapStyleWord(true);
        textoArea.setEditable(false);
        textoArea.setBackground(Color.WHITE);
        textoArea.setBounds(50, 90, 450, 280);
        contentPanel.add(textoArea);
        
        // Botões
        JButton btnConhecerServicos = createButton("🧡 Conhecer serviços", 50, 380, new Color(255, 107, 38));
        JButton btnContato = createButton("Entre em contato", 250, 380, Color.WHITE);
        btnContato.setForeground(new Color(255, 107, 38));
        btnContato.setBorder(BorderFactory.createLineBorder(new Color(255, 107, 38), 2));
        
        // Ação do botão contato
        btnContato.addActionListener(e -> {
            new FormularioContatoFrame().setVisible(true);
        });
        
        contentPanel.add(btnConhecerServicos);
        contentPanel.add(btnContato);
        
        // Imagem (placeholder)
        JPanel imagemPanel = new JPanel();
        imagemPanel.setBounds(550, 30, 300, 400);
        imagemPanel.setBackground(new Color(50, 50, 50));
        imagemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        
        JLabel imagemLabel = new JLabel("Imagem da Veterinária", SwingConstants.CENTER);
        imagemLabel.setForeground(Color.WHITE);
        imagemPanel.add(imagemLabel);
        
        contentPanel.add(imagemPanel);
        
        add(contentPanel);
    }
    
    private JLabel createMenuLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setBounds(x, y, 100, 25);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return label;
    }
    
    private JButton createButton(String text, int x, int y, Color bgColor) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 180, 40);
        button.setBackground(bgColor);
        button.setForeground(bgColor == Color.WHITE ? new Color(255, 107, 38) : Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SobreNosFrame().setVisible(true);
        });
    }
}