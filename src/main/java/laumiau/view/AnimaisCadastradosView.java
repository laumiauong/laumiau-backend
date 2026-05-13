package laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import laumiau.model.Animal;

import laumiau.repository.AnimalRepository;
import laumiau.service.AnimalService;

public class AnimaisCadastradosView extends JFrame {

    private final Color LARANJA = new Color(255, 128, 0);
    private final Color FUNDO = new Color(255, 248, 241);
    private final Color TEXTO = new Color(31, 42, 68);
    private final Color CINZA = new Color(120, 130, 150);
    
    private AnimalService animalService;
    
    public AnimaisCadastradosView(AnimalService animalService) {

    this.animalService = animalService;

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

        new AnimalView(animalService);
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
        btnNovo.addActionListener(
        e -> new CadastroAnimalView(animalService)
);
        
        pesquisa.setPreferredSize(new Dimension(430, 45));
        
        barra.add(pesquisa, BorderLayout.WEST);
        barra.add(btnNovo, BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(0, 5, 24, 24));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(30, 0, 0, 0));

        for (Animal animal : animalService.listarTodos()) {
    grid.add(criarCard(
        animal.getId(),
        animal.getNome(),
        "#" + animal.getId(),
        animal.getEspecie(),
        animal.getIdade() + " Meses",
        animal.getStatus().toString(),
        animal.getCaminhoFoto()
    ));
}

        fundo.add(barra, BorderLayout.NORTH);
        fundo.add(grid, BorderLayout.CENTER);

        return fundo;
    }

    private JPanel criarCard(Long animalId, String nome, String id, String especie,
                         String idade, String status, String caminhoFoto) {

    JPanel card = new RoundedPanel(25, Color.WHITE);

    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

    card.setBorder(new EmptyBorder(0, 0, 12, 0));

    card.setPreferredSize(new Dimension(250, 340));

    card.setMaximumSize(new Dimension(250, 340));

    // FOTO
    JLabel foto = new JLabel("", SwingConstants.CENTER);

    foto.setOpaque(true);

    foto.setBackground(new Color(245, 246, 250));

    foto.setPreferredSize(new Dimension(250, 200));

    foto.setMinimumSize(new Dimension(250, 200));

    foto.setMaximumSize(new Dimension(250, 200));

    if (caminhoFoto != null && !caminhoFoto.isBlank()) {

        ImageIcon imagem = new ImageIcon(caminhoFoto);

        Image img = imagem.getImage().getScaledInstance(
                250,
                200,
                Image.SCALE_SMOOTH
        );

        foto.setIcon(new ImageIcon(img));

    } else {

        foto.setText("🐾");

        foto.setFont(new Font("SansSerif", Font.PLAIN, 42));

        foto.setForeground(new Color(150, 160, 175));
    }

    JPanel topo = new JPanel(new BorderLayout());

    topo.setOpaque(false);

    topo.add(foto, BorderLayout.CENTER);

    // EDITAR
    JLabel editar = new JLabel("✎");

    editar.setFont(new Font("SansSerif", Font.BOLD, 22));

    editar.setForeground(LARANJA);

    JPanel painelEditar = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 10, 10)
    );

    painelEditar.setOpaque(false);

    painelEditar.add(editar);
    
    editar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

editar.addMouseListener(new java.awt.event.MouseAdapter() {

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        JOptionPane.showMessageDialog(
                null,
                "Tela de edição em desenvolvimento!"
        );

    }
});

    topo.add(painelEditar, BorderLayout.NORTH);

    // INFO
    JPanel info = new JPanel();

    info.setOpaque(false);

    info.setBorder(new EmptyBorder(14, 18, 0, 18));

    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

    JLabel lblNome = new JLabel(nome);

    lblNome.setFont(new Font("SansSerif", Font.BOLD, 18));

    lblNome.setForeground(TEXTO);

    JLabel lblId = new JLabel("ID: " + id);

    lblId.setFont(new Font("SansSerif", Font.PLAIN, 13));

    lblId.setForeground(CINZA);

    JPanel tags = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 0)
    );

    tags.setOpaque(false);

    tags.add(tag(
            especie,
            new Color(230, 190, 255),
            new Color(100, 45, 180)
    ));

    tags.add(tag(
            idade,
            new Color(255, 235, 185),
            new Color(150, 80, 0)
    ));

    Color corStatus = status.equalsIgnoreCase("ADOTADO")
            ? new Color(225, 229, 235)
            : new Color(185, 245, 220);

    tags.add(tag(
            status,
            corStatus,
            new Color(20, 100, 75)
    ));

    info.add(lblNome);

    info.add(Box.createVerticalStrut(5));

    info.add(lblId);

    info.add(Box.createVerticalStrut(12));

    info.add(tags);

    // EXCLUIR
    JLabel excluir = new JLabel("🗑");

    excluir.setFont(new Font("SansSerif", Font.BOLD, 18));

    excluir.setForeground(new Color(255, 90, 100));

    JPanel rodape = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 18, 0)
    );

    rodape.setOpaque(false);

    rodape.add(excluir);
    
    excluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

excluir.addMouseListener(new java.awt.event.MouseAdapter() {

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                "Deseja excluir esse animal?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {

            animalService.remover(animalId);

            dispose();

            new AnimaisCadastradosView(animalService);
        }
    }
});

    // ADICIONA
    card.add(topo);

    card.add(info);

    card.add(Box.createVerticalGlue());

    card.add(rodape);

    return card;
}
    private JLabel tag(String texto, Color fundo, Color corTexto) {

    JLabel label = new JLabel(texto);

    label.setOpaque(true);

    label.setBackground(fundo);

    label.setForeground(corTexto);

    label.setFont(new Font("SansSerif", Font.BOLD, 13));

    label.setBorder(new EmptyBorder(8, 14, 8, 14));

    return label;
}
    public static void main(String[] args) {

    EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("laumiau");

    EntityManager em = emf.createEntityManager();

    AnimalRepository repository =
            new AnimalRepository(em);

    AnimalService service =
            new AnimalService(repository);

    SwingUtilities.invokeLater(
            () -> new AnimaisCadastradosView(service)
    );
}
}