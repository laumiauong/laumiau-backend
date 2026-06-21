package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import laumiau.controller.LoginController;
import laumiau.infra.JPAUtil;
import laumiau.model.Animal;
import laumiau.model.Cliente;
import laumiau.model.Porte;
import laumiau.model.Sexo;
import laumiau.model.StatusAnimal;
import laumiau.repository.UsuarioRepository;
import laumiau.service.AnimalService;
import laumiau.service.UsuarioService;
import java.util.List;

public class AnimalView extends JFrame {

    private final Color LARANJA = new Color(255, 107, 43);
    private final Color TEXTO = new Color(15, 23, 42);
    private final Color CINZA = new Color(160, 170, 185);
    private final Color FUNDO = new Color(253, 247, 242);
    private final Color CARD_BG = Color.WHITE;

    private AnimalService animalService;
    private UsuarioService usuarioService;
    private Cliente clienteLogado;


    public AnimalView(AnimalService animalService) {
        this(animalService, null, null);
    }
    public AnimalView(AnimalService animalService, UsuarioService usuarioService) {
        this(animalService, usuarioService, null);
    }

    public AnimalView(AnimalService animalService, UsuarioService usuarioService, Cliente clienteLogado) {
        this.animalService = animalService;
        this.usuarioService = usuarioService;
        this.clienteLogado = clienteLogado;

        setTitle("LAU & MIAU - Animais");
        setSize(1280, 780);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(FUNDO);
        setLayout(new BorderLayout());

        add(criarTopo(), BorderLayout.NORTH);
        add(criarConteudo(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel criarTopo() {
        return new NavbarPadrao("Home", animalService, usuarioService);
    }

    private void popularBancoSeVazio() {
        try {
            List<Animal> existentes = animalService.listarTodos();
            List<String> nomesExistentes = new java.util.ArrayList<>();
            if (existentes != null) {
                for (Animal a : existentes) {
                    nomesExistentes.add(a.getNome().toLowerCase());
                }
            }

            Animal[] animaisPadrao = {
                    criarAnimal("Fofuxo",   "Gato",     "SRD", 12, Sexo.MACHO, true,  Porte.PEQUENO, "ONG Lau & Miau", "img/imgGATO1.png"),
                    criarAnimal("Princesa",  "Gato",     "SRD",  8, Sexo.FEMEA, true,  Porte.PEQUENO, "ONG Lau & Miau", "img/imgGATO2.png"),
                    criarAnimal("Bonitão",   "Gato",     "SRD", 24, Sexo.MACHO, false, Porte.MEDIO,   "ONG Lau & Miau", "img/imgGATO3.png"),
                    criarAnimal("Bebe",      "Gato",     "SRD",  6, Sexo.FEMEA, true,  Porte.PEQUENO, "ONG Lau & Miau", "img/imgGATO4.png"),
                    criarAnimal("Rabinho",   "Cachorro", "SRD", 36, Sexo.MACHO, true,  Porte.GRANDE,  "ONG Lau & Miau", "img/imgCACHORRO1.png"),
                    criarAnimal("Charmosa",  "Gato",     "SRD", 18, Sexo.FEMEA, false, Porte.PEQUENO, "ONG Lau & Miau", "img/imgGATO6.png"),
                    criarAnimal("Banguela",  "Cachorro", "SRD", 14, Sexo.MACHO, true,  Porte.MEDIO,   "ONG Lau & Miau", "img/imgCACHORRO2.png"),
                    criarAnimal("Preciosa",  "Gato",     "SRD", 10, Sexo.FEMEA, true,  Porte.PEQUENO, "ONG Lau & Miau", "img/imgGATO8.png"),
                    criarAnimal("Renê",      "Gato",     "SRD", 15, Sexo.MACHO, true,  Porte.PEQUENO, "ONG Lau & Miau", "img/imgGATO9.png"),
                    criarAnimal("Perninha",  "Gato",     "SRD", 20, Sexo.MACHO, false, Porte.PEQUENO, "ONG Lau & Miau", "img/imgGATO10.png")
            };

            for (Animal a : animaisPadrao) {
                if (!nomesExistentes.contains(a.getNome().toLowerCase())) {
                    animalService.cadastrar(a);
                    System.out.println("✅ Cadastrado: " + a.getNome());
                }
            }

        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível popular o banco: " + e.getMessage());
        }
    }

    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(FUNDO);
        conteudo.setBorder(new EmptyBorder(36, 40, 40, 40));

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);
        cabecalho.setBorder(new EmptyBorder(0, 0, 24, 0));

        JLabel titulo = new JLabel("Adotar pet");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(TEXTO);

        JLabel verTodos = new JLabel("Ver todos →");
        verTodos.setFont(new Font("SansSerif", Font.BOLD, 14));
        verTodos.setForeground(LARANJA);
        verTodos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        verTodos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    boolean isAdmin = (clienteLogado != null &&
                            clienteLogado.getNome() != null &&
                            clienteLogado.getNome().toLowerCase().contains("admin"));

                    new AnimaisCadastradosView(animalService, clienteLogado, isAdmin);
                    dispose();
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null,
                            "Erro ao abrir a listagem de animais: " + erro.getMessage(),
                            "Erro de Carregamento",
                            JOptionPane.ERROR_MESSAGE);
                    erro.printStackTrace();
                }
            }
        });

        cabecalho.add(titulo, BorderLayout.WEST);
        cabecalho.add(verTodos, BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(0, 5, 18, 18));
        grid.setOpaque(false);

        popularBancoSeVazio();

        try {
            List<Animal> animaisDoBanco = animalService.listarTodos();
            if (animaisDoBanco != null) {
                for (Animal animal : animaisDoBanco) {
                    if (animal.getStatus() == StatusAnimal.DISPONIVEL) {
                        grid.add(criarCard(animal));
                    }
                }
            }
        } catch (Exception e) {
            JLabel lblErro = new JLabel("Erro ao carregar animais: " + e.getMessage(), SwingConstants.CENTER);
            lblErro.setForeground(Color.RED);
            grid.add(lblErro);
            e.printStackTrace();
        }

        conteudo.add(cabecalho, BorderLayout.NORTH);
        conteudo.add(grid, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.setBackground(FUNDO);
        scroll.getViewport().setBackground(FUNDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    private Animal criarAnimal(String nome, String especie, String raca, int idade,
                               Sexo sexo, boolean vacinado, Porte porte,
                               String responsavel, String caminhoFoto) {
        Animal a = new Animal(nome, especie, raca, idade, sexo, vacinado, porte, caminhoFoto);
        a.setResponsavel(responsavel);
        return a;
    }

    private JPanel criarCard(Animal animal) {
        JPanel sombra = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                for (int i = 4; i >= 1; i--) {
                    g2.setColor(new Color(0, 0, 0, 8));
                    g2.fill(new RoundRectangle2D.Float(i, i + 2, getWidth() - i * 2, getHeight() - i * 2, 20, 20));
                }

                g2.setColor(CARD_BG);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 4, getHeight() - 4, 20, 20));
                g2.dispose();
            }
        };

        sombra.setOpaque(false);
        sombra.setBorder(new EmptyBorder(0, 0, 4, 4));
        sombra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel areaImg = new JPanel(null);
        areaImg.setOpaque(false);
        areaImg.setPreferredSize(new Dimension(0, 200));

        JLabel foto = carregarImagem(animal.getCaminhoFoto(), 230, 200);
        foto.setBounds(0, 0, 230, 200);

        boolean[] favoritado = {false};

        JButton coracao = new JButton("♡") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillOval(1, 2, getWidth() - 1, getHeight() - 1);
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {}
        };

        coracao.setFont(new Font("SansSerif", Font.BOLD, 16));
        coracao.setForeground(CINZA);
        coracao.setContentAreaFilled(false);
        coracao.setBorderPainted(false);
        coracao.setFocusPainted(false);
        coracao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        coracao.setSize(32, 32);
        coracao.setLocation(190, 12);

        coracao.addActionListener(e -> {
            favoritado[0] = !favoritado[0];
            coracao.setForeground(favoritado[0] ? LARANJA : CINZA);
            coracao.setText(favoritado[0] ? "♥" : "♡");
        });

        areaImg.add(foto);
        areaImg.add(coracao);

        areaImg.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = areaImg.getWidth();
                int h = areaImg.getHeight();
                foto.setBounds(0, 0, w, h);
                coracao.setLocation(w - 42, 12);
            }
        });

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(14, 16, 16, 16));

        JLabel lblNome = new JLabel(animal.getNome());
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblNome.setForeground(TEXTO);
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        String sexoTexto = animal.getSexo() == Sexo.FEMEA ? "Fêmea" : "Macho";
        Color corSexo = animal.getSexo() == Sexo.FEMEA ? new Color(236, 72, 153) : new Color(59, 130, 246);

        JPanel sexoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        sexoRow.setOpaque(false);
        sexoRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel bullet = new JLabel("● ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(corSexo);
                int sz = 10;
                int y = (getHeight() - sz) / 2;
                g2.fillOval(0, y, sz, sz);
                g2.dispose();
            }
        };
        bullet.setPreferredSize(new Dimension(16, 18));

        JLabel lblSexo = new JLabel(sexoTexto);
        lblSexo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblSexo.setForeground(corSexo);

        sexoRow.add(bullet);
        sexoRow.add(lblSexo);

        JPanel localRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        localRow.setOpaque(false);
        localRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel pinIcon = new JLabel("📍");
        pinIcon.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel lblLocal = new JLabel("Foz do Iguaçu, Centro");
        lblLocal.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLocal.setForeground(CINZA);

        localRow.add(pinIcon);
        localRow.add(lblLocal);

        info.add(lblNome);
        info.add(Box.createVerticalStrut(6));
        info.add(sexoRow);
        info.add(Box.createVerticalStrut(4));
        info.add(localRow);

        sombra.add(areaImg, BorderLayout.NORTH);
        sombra.add(info, BorderLayout.CENTER);

        MouseAdapter cliqueCard = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirDetalhes(animal);
            }
        };
        sombra.addMouseListener(cliqueCard);
        foto.addMouseListener(cliqueCard);
        info.addMouseListener(cliqueCard);

        return sombra;
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

        try {
            java.net.URL url = getClass().getClassLoader().getResource(animal.getCaminhoFoto());
            if (url != null) {
                ImageIcon iconImg = new ImageIcon(url);
                Image img = iconImg.getImage().getScaledInstance(560, 620, Image.SCALE_SMOOTH);
                imagem.setIcon(new ImageIcon(img));
            } else {
                imagem.setText("🐾");
                imagem.setFont(new Font("SansSerif", Font.PLAIN, 80));
                imagem.setForeground(CINZA);
            }
        } catch (Exception e) {
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
        infos.add(criarBox(animal.getRaca(), "Raça"));
        infos.add(criarBox(animal.getEspecie(), "Espécie"));
        infos.add(criarBox(animal.getIdade() + " meses", "Idade"));

        JPanel vacina = new AnimaisCadastradosView.RoundedPanel(
                18,
                animal.isVacinado() ? new Color(220, 255, 230) : new Color(255, 235, 235)
        );
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

        JLabel txtResp = new JLabel(
                "<html><span style='color:#999999'>Com quem está:</span><br><b>"
                        + nomeResponsavel + "</b></html>"
        );
        txtResp.setFont(new Font("SansSerif", Font.PLAIN, 15));

        responsavel.add(avatarResp);
        responsavel.add(txtResp);

        JTextArea descricao = new JTextArea(
                "Conheça o " + animal.getNome() + "! Esse lindo pet está aguardando "
                        + "uma adoção responsável na " + nomeResponsavel + "."
        );
        descricao.setEditable(false);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setOpaque(false);
        descricao.setFont(new Font("SansSerif", Font.PLAIN, 17));
        descricao.setForeground(new Color(70, 70, 80));

        JButton adotar = new AnimaisCadastradosView.RoundedButton("❤ Quero adotar", LARANJA, Color.WHITE);
        adotar.setFont(new Font("SansSerif", Font.BOLD, 18));
        adotar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));


        adotar.addActionListener(e -> {
            if (clienteLogado != null) {
                tela.dispose();
                dispose();
                new AdocaoView(animalService, animal, clienteLogado).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(tela,
                        "Para adotar um pet, você precisa fazer login ou se cadastrar no sistema primeiro.",
                        "Acesso Restrito",
                        JOptionPane.INFORMATION_MESSAGE);
                tela.dispose();
                dispose();
                UsuarioService service = obterUsuarioService();
                new LoginView(service, new LoginController(service), animalService).setVisible(true);
            }
        });

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
        JPanel box = new AnimaisCadastradosView.RoundedPanel(18, Color.WHITE);
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

    private JLabel carregarImagem(String caminho, int largura, int altura) {
        try {
            java.net.URL url = getClass().getClassLoader().getResource(caminho);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                JLabel lbl = new JLabel(new ImageIcon(img));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel placeholder = new JLabel("🐾", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(240, 234, 228));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        placeholder.setFont(new Font("SansSerif", Font.PLAIN, 52));
        placeholder.setPreferredSize(new Dimension(largura, altura));
        placeholder.setOpaque(false);

        return placeholder;
    }

    private UsuarioService obterUsuarioService() {
        if (usuarioService != null) {
            return usuarioService;
        }
        return new UsuarioService(new UsuarioRepository(JPAUtil.getEntityManager()));
    }
}
