package br.com.laumiau.view;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import laumiau.model.Animal;
import laumiau.model.Sexo;
import laumiau.repository.AnimalRepository;
import laumiau.service.AnimalService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AnimaisCadastradosView extends JFrame {

    private final Color LARANJA = new Color(255, 107, 43);
    private final Color FUNDO = new Color(253, 247, 242);
    private final Color TEXTO = new Color(15, 23, 42);
    private final Color CINZA = new Color(160, 170, 185);

    private AnimalService animalService;
    private JPanel grid;
    private JTextField pesquisa;

    private final String PLACEHOLDER = "Buscar por nome ou ID";

    public AnimaisCadastradosView(AnimalService animalService) {
        this.animalService = animalService;

        setTitle("Animais Cadastrados");
        setSize(1280, 780);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(criarTopo(), BorderLayout.NORTH);
        add(criarConteudo(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel criarTopo() {
        return new NavbarPadrao("Animais", animalService);
    }

    private JScrollPane criarConteudo() {
        JPanel fundo = new JPanel(new BorderLayout());
        fundo.setBackground(FUNDO);
        fundo.setBorder(new EmptyBorder(35, 35, 35, 35));

        JPanel barra = new RoundedPanel(25, Color.WHITE);
        barra.setLayout(new BorderLayout());
        barra.setBorder(new EmptyBorder(15, 20, 15, 20));

        pesquisa = new JTextField();
        pesquisa.setText(PLACEHOLDER);
        pesquisa.setFont(new Font("SansSerif", Font.PLAIN, 15));
        pesquisa.setForeground(Color.GRAY);
        pesquisa.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        pesquisa.setPreferredSize(new Dimension(430, 45));

        pesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (pesquisa.getText().equals(PLACEHOLDER)) {
                    pesquisa.setText("");
                    pesquisa.setForeground(TEXTO);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (pesquisa.getText().isEmpty()) {
                    pesquisa.setForeground(Color.GRAY);
                    pesquisa.setText(PLACEHOLDER);
                }
            }
        });

        JButton btnNovo = new RoundedButton("+  Novo Cadastro", LARANJA, Color.WHITE);
        btnNovo.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnNovo.addActionListener(e -> new CadastroAnimalView(animalService));

        barra.add(pesquisa, BorderLayout.WEST);
        barra.add(btnNovo, BorderLayout.EAST);

        grid = new JPanel(new GridLayout(0, 5, 24, 24));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(30, 0, 0, 0));

        carregarAnimais("");

        pesquisa.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { carregarAnimais(pesquisa.getText()); }
            @Override public void removeUpdate(DocumentEvent e) { carregarAnimais(pesquisa.getText()); }
            @Override public void changedUpdate(DocumentEvent e) { carregarAnimais(pesquisa.getText()); }
        });

        fundo.add(barra, BorderLayout.NORTH);
        fundo.add(grid, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(fundo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(FUNDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    private void carregarAnimais(String filtro) {
        grid.removeAll();

        String texto = filtro.equals(PLACEHOLDER) ? "" : filtro.toLowerCase().trim();

        for (Animal animal : animalService.listarTodos()) {
            boolean correspondeNome = animal.getNome().toLowerCase().contains(texto);
            boolean correspondeId = String.valueOf(animal.getId()).contains(texto);

            if (texto.isEmpty() || correspondeNome || correspondeId) {
                grid.add(criarCard(animal));
            }
        }

        grid.revalidate();
        grid.repaint();
    }

    private JPanel criarCard(Animal animal) {
        JPanel card = new RoundedPanel(25, Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(250, 360));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel foto = new JLabel("", SwingConstants.CENTER);
        foto.setPreferredSize(new Dimension(250, 200));
        foto.setOpaque(true);
        foto.setBackground(new Color(245, 246, 250));

        if (animal.getCaminhoFoto() != null && !animal.getCaminhoFoto().isBlank()) {
            ImageIcon imagem = new ImageIcon(animal.getCaminhoFoto());
            Image img = imagem.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
            foto.setIcon(new ImageIcon(img));
        } else {
            foto.setText("🐾");
            foto.setFont(new Font("SansSerif", Font.PLAIN, 46));
            foto.setForeground(CINZA);
        }

        JPanel topoFoto = new JPanel(new BorderLayout());
        topoFoto.setPreferredSize(new Dimension(250, 200));
        topoFoto.setOpaque(false);

        JPanel acoes = new JPanel();
        acoes.setOpaque(false);
        acoes.setLayout(new BoxLayout(acoes, BoxLayout.Y_AXIS));
        acoes.setBorder(new EmptyBorder(10, 0, 10, 10));

        JLabel editar = new JLabel("✎");
        editar.setFont(new Font("SansSerif", Font.BOLD, 22));
        editar.setForeground(LARANJA);
        editar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editar.setAlignmentX(Component.CENTER_ALIGNMENT);
        editar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
                dispose();
                new CadastroAnimalView(animalService, animal);
            }
        });

        JLabel excluir = new JLabel("🗑");
        excluir.setFont(new Font("SansSerif", Font.BOLD, 18));
        excluir.setForeground(new Color(255, 90, 100));
        excluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        excluir.setAlignmentX(Component.CENTER_ALIGNMENT);
        excluir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
                int confirmar = JOptionPane.showConfirmDialog(null,
                        "Deseja excluir o animal " + animal.getNome() + "?",
                        "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
                if (confirmar == JOptionPane.YES_OPTION) {
                    try {
                        animalService.remover(animal.getId());
                        carregarAnimais(pesquisa.getText());
                        JOptionPane.showMessageDialog(null, "Animal excluído com sucesso!");
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, erro.getMessage());
                    }
                }
            }
        });

        acoes.add(editar);

        JLayeredPane camada = new JLayeredPane();
        camada.setPreferredSize(new Dimension(250, 200));
        foto.setBounds(0, 0, 250, 200);
        acoes.setBounds(205, 10, 35, 80);
        camada.add(foto, Integer.valueOf(0));
        camada.add(acoes, Integer.valueOf(1));
        topoFoto.add(camada, BorderLayout.CENTER);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(14, 16, 16, 16));

        JLabel id = new JLabel("ID #" + animal.getId());
        id.setFont(new Font("SansSerif", Font.BOLD, 12));
        id.setForeground(new Color(140, 140, 150));

        JLabel nome = new JLabel(animal.getNome());
        nome.setFont(new Font("SansSerif", Font.BOLD, 18));
        nome.setForeground(TEXTO);

        Color corSexo = animal.getSexo() == Sexo.FEMEA ? new Color(236, 72, 153) : new Color(59, 130, 246);
        JLabel sexo = new JLabel("● " + (animal.getSexo() == Sexo.FEMEA ? "Fêmea" : "Macho"));
        sexo.setForeground(corSexo);
        sexo.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel local = new JLabel("📍 Foz do Iguaçu, Centro");
        local.setForeground(CINZA);
        local.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel linhaExcluir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        linhaExcluir.setOpaque(false);
        excluir.setFont(new Font("SansSerif", Font.BOLD, 17));
        linhaExcluir.add(excluir);

        info.add(id);
        info.add(Box.createVerticalStrut(4));
        info.add(nome);
        info.add(Box.createVerticalStrut(8));
        info.add(sexo);
        info.add(Box.createVerticalStrut(12));
        info.add(local);
        info.add(Box.createVerticalGlue());
        info.add(Box.createVerticalStrut(12));
        info.add(linhaExcluir);

        card.add(topoFoto, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);

        MouseAdapter clique = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { abrirDetalhes(animal); }
        };

        card.addMouseListener(clique);
        foto.addMouseListener(clique);
        info.addMouseListener(clique);

        return card;
    }

    private void abrirDetalhes(Animal animal) {
        JFrame tela = new JFrame(animal.getNome());
        tela.setSize(1250, 760);
        tela.setLocationRelativeTo(null);
        tela.setLayout(new BorderLayout());

        JPanel container = new JPanel(new GridLayout(1, 2, 45, 0));
        container.setBorder(new EmptyBorder(40, 50, 40, 50));
        container.setBackground(new Color(250, 248, 245));

        JLabel imagem = new JLabel("", SwingConstants.CENTER);
        imagem.setOpaque(true);
        imagem.setBackground(Color.WHITE);

        if (animal.getCaminhoFoto() != null && !animal.getCaminhoFoto().isBlank()) {
            ImageIcon icon = new ImageIcon(animal.getCaminhoFoto());
            Image img = icon.getImage().getScaledInstance(560, 620, Image.SCALE_SMOOTH);
            imagem.setIcon(new ImageIcon(img));
        } else {
            imagem.setText("🐾");
            imagem.setFont(new Font("SansSerif", Font.PLAIN, 80));
            imagem.setForeground(CINZA);
        }

        JPanel direita = new JPanel();
        direita.setOpaque(false);
        direita.setLayout(new BoxLayout(direita, BoxLayout.Y_AXIS));

        JLabel nome = new JLabel(animal.getNome());
        nome.setFont(new Font("SansSerif", Font.BOLD, 40));
        nome.setForeground(Color.BLACK);

        JLabel cidade = new JLabel("📍 Foz do Iguaçu, Centro");
        cidade.setForeground(Color.GRAY);
        cidade.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel infos = new JPanel(new GridLayout(1, 4, 12, 0));
        infos.setOpaque(false);
        infos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        infos.add(criarBox(animal.getSexo() == Sexo.FEMEA ? "Fêmea" : "Macho", "Sexo"));
        infos.add(criarBox(textoOuPadrao(animal.getCor(), "Não informado"), "Cor"));
        infos.add(criarBox(animal.getPorte() != null ? animal.getPorte().toString() : "Não informado", "Porte"));
        infos.add(criarBox(textoOuPadrao(animal.getPeso(), "Não informado"), "Peso"));

        JPanel vacina = new RoundedPanel(18, animal.isVacinado() ? new Color(220, 255, 230) : new Color(255, 235, 235));
        vacina.setLayout(new FlowLayout(FlowLayout.LEFT));
        vacina.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        JLabel vacinado = new JLabel(animal.isVacinado() ? "✔ Vacinado" : "✖ Não vacinado");
        vacinado.setFont(new Font("SansSerif", Font.BOLD, 14));
        vacina.add(vacinado);

        JPanel responsavel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        responsavel.setOpaque(false);
        responsavel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel avatarResp = new JLabel("ONG", SwingConstants.CENTER);
        avatarResp.setOpaque(true);
        avatarResp.setBackground(new Color(238, 238, 238));
        avatarResp.setForeground(Color.BLACK);
        avatarResp.setFont(new Font("SansSerif", Font.BOLD, 13));
        avatarResp.setPreferredSize(new Dimension(52, 52));

        String nomeResponsavel = textoOuPadrao(animal.getResponsavel(), "ONG Lau & Miau");

        JLabel txtResp = new JLabel("<html><span style='color:#999999'>Com quem está:</span><br><b>" + nomeResponsavel + "</b></html>");
        txtResp.setFont(new Font("SansSerif", Font.PLAIN, 15));

        responsavel.add(avatarResp);
        responsavel.add(txtResp);

        JTextArea descricao = new JTextArea(textoOuPadrao(animal.getDescricao(), "Sem descrição cadastrada."));
        descricao.setEditable(false);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setOpaque(false);
        descricao.setFont(new Font("SansSerif", Font.PLAIN, 17));
        descricao.setForeground(new Color(70, 70, 80));

        JButton adotar = new RoundedButton("❤ Quero adotar", LARANJA, Color.WHITE);
        adotar.setFont(new Font("SansSerif", Font.BOLD, 18));
        adotar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        adotar.addActionListener(e -> JOptionPane.showMessageDialog(tela,
                "Solicitação enviada com sucesso!\n\nResponsável: " + nomeResponsavel,
                "Adoção", JOptionPane.INFORMATION_MESSAGE));

        direita.add(nome);
        direita.add(Box.createVerticalStrut(8));
        direita.add(cidade);
        direita.add(Box.createVerticalStrut(30));
        direita.add(infos);
        direita.add(Box.createVerticalStrut(20));
        direita.add(vacina);
        direita.add(Box.createVerticalStrut(25));
        direita.add(responsavel);
        direita.add(Box.createVerticalStrut(25));
        direita.add(descricao);
        direita.add(Box.createVerticalGlue());
        direita.add(adotar);

        container.add(imagem);
        container.add(direita);

        tela.add(container, BorderLayout.CENTER);
        tela.setVisible(true);
    }

    private JPanel criarBox(String valor, String titulo) {
        JPanel box = new RoundedPanel(18, Color.WHITE);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel v = new JLabel(valor);
        v.setFont(new Font("SansSerif", Font.BOLD, 15));
        v.setForeground(Color.BLACK);

        JLabel t = new JLabel(titulo);
        t.setFont(new Font("SansSerif", Font.PLAIN, 13));
        t.setForeground(Color.GRAY);

        box.add(v);
        box.add(Box.createVerticalStrut(4));
        box.add(t);

        return box;
    }

    private String textoOuPadrao(String texto, String padrao) {
        if (texto == null || texto.trim().isEmpty()) return padrao;
        return texto;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("laumiau");
        EntityManager em = emf.createEntityManager();
        AnimalRepository repository = new AnimalRepository(em);
        AnimalService service = new AnimalService(repository);
        SwingUtilities.invokeLater(() -> new AnimaisCadastradosView(service));
    }

    static class RoundedPanel extends JPanel {
        private int radius;
        private Color bg;

        public RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            this.bg = bg;
            setOpaque(false);
            setBackground(bg);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
        }
    }

    static class RoundedButton extends JButton {
        private Color bg;

        public RoundedButton(String text, Color bg, Color fg) {
            super(text);
            this.bg = bg;
            setForeground(fg);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setBorder(new EmptyBorder(12, 24, 12, 24));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
            g2.dispose();
        }
    }
}