package laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AnimaisCadastradosView extends JFrame {

    private final Color LARANJA = new Color(255, 128, 0);
    private final Color FUNDO = new Color(255, 248, 241);
    private final Color TEXTO = new Color(31, 42, 68);
    private final Color CINZA = new Color(120, 130, 150);

    public AnimaisCadastradosView() {
        setTitle("Animais Cadastrados");
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(criarTopo(), BorderLayout.NORTH);
        add(criarConteudo(), BorderLayout.CENTER);

        setVisible(true);
    }

   private JPanel criarTopo() {
    JPanel topo = new JPanel(new BorderLayout());
    topo.setBackground(Color.WHITE);
    topo.setBorder(new EmptyBorder(15, 25, 15, 25));

    JLabel titulo = new JLabel("←  Animais Cadastrados");
    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
    titulo.setForeground(TEXTO);
    titulo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    titulo.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        dispose();

        new AnimalView();
    }
});

    JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    adminPanel.setOpaque(false);

    JLabel adminTexto = new JLabel("Admin");
    adminTexto.setFont(new Font("SansSerif", Font.PLAIN, 14));
    adminTexto.setForeground(TEXTO);

    JLabel avatar = new JLabel("A", SwingConstants.CENTER);
    avatar.setOpaque(true);
    avatar.setBackground(LARANJA);
    avatar.setForeground(Color.WHITE);
    avatar.setFont(new Font("SansSerif", Font.BOLD, 14));
    avatar.setPreferredSize(new Dimension(32, 32));

    adminPanel.add(adminTexto);
    adminPanel.add(avatar);

    topo.add(titulo, BorderLayout.WEST);
    topo.add(adminPanel, BorderLayout.EAST);

    return topo;
}

    private JPanel criarConteudo() {
        JPanel fundo = new JPanel();
        fundo.setBackground(FUNDO);
        fundo.setLayout(new BorderLayout());
        fundo.setBorder(new EmptyBorder(35, 35, 35, 35));

        JPanel barra = new RoundedPanel(25, Color.WHITE);
        barra.setLayout(new BorderLayout());
        barra.setBorder(new EmptyBorder(15, 20, 15, 20));

        JTextField pesquisa = new JTextField("Pesquisar animais por nome ou ID...");
        pesquisa.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pesquisa.setForeground(CINZA);
        pesquisa.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton btnNovo = new RoundedButton("+  Novo Cadastro", LARANJA, Color.WHITE);
        btnNovo.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnNovo.addActionListener(e -> new CadastroAnimalView());
        
        pesquisa.setPreferredSize(new Dimension(430, 45));
        
        barra.add(pesquisa, BorderLayout.WEST);
        barra.add(btnNovo, BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(0, 3, 25, 25));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(30, 0, 0, 0));

        grid.add(criarCard("Mia", "#12345", "Gato", "2 Anos", "Disponível"));
        grid.add(criarCard("Rex", "#12346", "Cachorro", "5 Meses", "Em Adoção"));
        grid.add(criarCard("Luna", "#12347", "Gato", "1 Ano", "Disponível"));
        grid.add(criarCard("Thor", "#12348", "Cachorro", "3 Anos", "Disponível"));
        grid.add(criarCard("Felix", "#12349", "Gato", "8 Meses", "Disponível"));
        grid.add(criarCard("Bela", "#12350", "Cachorro", "4 Anos", "Adotado"));
        grid.add(criarCard("Milo", "#12351", "Gato", "6 Anos", "Disponível"));
        grid.add(criarCard("Kiko", "#12352", "Cachorro", "1 Mês", "Em Adoção"));

        fundo.add(barra, BorderLayout.NORTH);
        fundo.add(grid, BorderLayout.CENTER);

        return fundo;
    }

    private JPanel criarCard(String nome, String id, String especie, String idade, String status) {
        JPanel card = new RoundedPanel(25, Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel foto = new JLabel("🐾", SwingConstants.CENTER);
        foto.setFont(new Font("SansSerif", Font.PLAIN, 42));
        foto.setForeground(new Color(150, 160, 175));
        foto.setOpaque(true);
        foto.setBackground(new Color(245, 246, 250));
        foto.setPreferredSize(new Dimension(100, 100));

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblNome.setForeground(TEXTO);

        JLabel lblId = new JLabel("ID: " + id);
        lblId.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblId.setForeground(CINZA);

        JPanel tags = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        tags.setOpaque(false);
        tags.add(tag(especie, new Color(230, 190, 255), new Color(100, 45, 180)));
        tags.add(tag(idade, new Color(255, 235, 185), new Color(150, 80, 0)));

        Color corStatus = status.equals("Adotado")
                ? new Color(225, 229, 235)
                : new Color(185, 245, 220);

        tags.add(tag(status, corStatus, new Color(20, 100, 75)));

        info.add(lblNome);
        info.add(Box.createVerticalStrut(5));
        info.add(lblId);
        info.add(Box.createVerticalStrut(15));
        info.add(tags);

        JPanel icones = new JPanel();
        icones.setOpaque(false);
        icones.setLayout(new BoxLayout(icones, BoxLayout.Y_AXIS));

        JLabel editar = new JLabel("✎");
        editar.setFont(new Font("SansSerif", Font.BOLD, 22));
        editar.setForeground(LARANJA);

        JLabel excluir = new JLabel("🗑");
        excluir.setFont(new Font("SansSerif", Font.BOLD, 18));
        excluir.setForeground(new Color(255, 90, 100));

        icones.add(editar);
        icones.add(Box.createVerticalStrut(30));
        icones.add(excluir);

        card.add(foto, BorderLayout.WEST);
        card.add(info, BorderLayout.CENTER);
        card.add(icones, BorderLayout.EAST);

        return card;
    }

    private JLabel tag(String texto, Color fundo, Color corTexto) {
        JLabel label = new JLabel(texto);
        label.setOpaque(true);
        label.setBackground(fundo);
        label.setForeground(corTexto);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setBorder(new EmptyBorder(6, 12, 6, 12));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AnimaisCadastradosView::new);
    }
}