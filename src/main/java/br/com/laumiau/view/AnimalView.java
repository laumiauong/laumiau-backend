package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import laumiau.service.AnimalService;

public class AnimalView extends JFrame {

    private final Color LARANJA = new Color(255, 107, 43);
    private final Color LARANJA_HOVER = new Color(255, 140, 80);
    private final Color TEXTO = new Color(15, 23, 42);
    private final Color CINZA = new Color(160, 170, 185);
    private final Color FUNDO = new Color(253, 247, 242);
    private final Color CARD_BG = Color.WHITE;

    private AnimalService animalService;

    public AnimalView(AnimalService animalService) {
        this.animalService = animalService;

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
        return new NavbarPadrao("Home", animalService);
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
                dispose();
                new AnimaisCadastradosView(animalService);
            }
        });

        cabecalho.add(titulo, BorderLayout.WEST);
        cabecalho.add(verTodos, BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(2, 5, 18, 18));
        grid.setOpaque(false);

        grid.add(criarCard("Fofuxo", "Macho", "img/imgGATO1.png"));
        grid.add(criarCard("Princesa", "Fêmea", "img/imgGATO2.png"));
        grid.add(criarCard("Bonitão", "Macho", "img/imgGATO3.png"));
        grid.add(criarCard("Bebe", "Fêmea", "img/imgGATO4.png"));
        grid.add(criarCard("Rabinho", "Macho", "img/imgCACHORRO1.png"));
        grid.add(criarCard("Charmosa", "Fêmea", "img/imgGATO6.png"));
        grid.add(criarCard("Banguela", "Macho", "img/imgCACHORRO2.png"));
        grid.add(criarCard("Preciosa", "Fêmea", "img/imgGATO8.png"));
        grid.add(criarCard("Renê", "Macho", "img/imgGATO9.png"));
        grid.add(criarCard("Perninha", "Macho", "img/imgGATO10.png"));

        conteudo.add(cabecalho, BorderLayout.NORTH);
        conteudo.add(grid, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.setBackground(FUNDO);
        scroll.getViewport().setBackground(FUNDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    private JPanel criarCard(String nome, String sexo, String caminhoImg) {
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

        JLabel foto = carregarImagem(caminhoImg, 230, 200);
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

        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblNome.setForeground(TEXTO);
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        Color corSexo = sexo.equals("Fêmea")
                ? new Color(236, 72, 153)
                : new Color(59, 130, 246);

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

        JLabel lblSexo = new JLabel(sexo);
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

        return sombra;
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
}