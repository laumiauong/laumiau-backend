package laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AnimalView extends JFrame {

    private final Color LARANJA = new Color(255, 128, 0);
    private final Color TEXTO = new Color(15, 23, 42);
    private final Color CINZA = new Color(120, 130, 150);

    public AnimalView() {
        setTitle("LAU & MIAU - Animais");
        setSize(1250, 760);
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
        topo.setBorder(new EmptyBorder(20, 35, 20, 35));

        JLabel logo = new JLabel("LAU🐾MIAU");
        logo.setFont(new Font("SansSerif", Font.BOLD, 28));
        logo.setForeground(LARANJA);

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        menu.setBackground(Color.WHITE);

        menu.add(itemMenu("Home", true));
        menu.add(itemMenu("Animais", false));
        menu.add(itemMenu("Sobre nós", false));

        JButton admin = new RoundedButton("👤 Admin", new Color(255, 237, 213), LARANJA);
        admin.addActionListener(e -> new AnimaisCadastradosView());

        topo.add(logo, BorderLayout.WEST);
        topo.add(menu, BorderLayout.CENTER);
        topo.add(admin, BorderLayout.EAST);

        return topo;
    }

    private JLabel itemMenu(String texto, boolean ativo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(ativo ? LARANJA : TEXTO);
        label.setBorder(new EmptyBorder(10, 22, 10, 22));

        if (ativo) {
            label.setOpaque(true);
            label.setBackground(new Color(255, 237, 213));
        }

        return label;
    }

    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(Color.WHITE);
        conteudo.setBorder(new EmptyBorder(20, 35, 35, 35));

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Adotar pet");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(TEXTO);

        JLabel verTodos = new JLabel("Ver todos");
        verTodos.setFont(new Font("SansSerif", Font.BOLD, 14));
        verTodos.setForeground(LARANJA);

        cabecalho.add(titulo, BorderLayout.WEST);
        cabecalho.add(verTodos, BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(0, 5, 25, 25));
        grid.setBackground(Color.WHITE);
        grid.setBorder(new EmptyBorder(25, 0, 0, 0));

        grid.add(criarCard("Fofuxo", "Macho", "img/gato1.png"));
        grid.add(criarCard("Princesa", "Fêmea", "img/gato2.png"));
        grid.add(criarCard("Bonitão", "Macho", "img/gato3.png"));
        grid.add(criarCard("Bebe", "Fêmea", "img/gato4.png"));
        grid.add(criarCard("Rabinho", "Macho", "img/cachorro1.png"));

        grid.add(criarCard("Charmosa", "Fêmea", "img/gato6.png"));
        grid.add(criarCard("Banquela", "Macho", "img/cachorro2.png"));
        grid.add(criarCard("Preciosa", "Fêmea", "img/gato8.png"));
        grid.add(criarCard("Renê", "Macho", "img/gato9.png"));
        grid.add(criarCard("Perninha", "Macho", "img/gato10.png"));

        conteudo.add(cabecalho, BorderLayout.NORTH);
        conteudo.add(grid, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    private JPanel criarCard(String nome, String sexo, String imagem) {
        JPanel card = new RoundedPanel(20, Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel areaImagem = new JPanel(new BorderLayout());
        areaImagem.setOpaque(false);

        JLabel foto = carregarImagem(imagem, 220, 190);
        areaImagem.add(foto, BorderLayout.CENTER);

        JLabel coracao = new JLabel("♡");
        coracao.setFont(new Font("SansSerif", Font.BOLD, 28));
        coracao.setForeground(CINZA);
        coracao.setBorder(new EmptyBorder(10, 0, 0, 15));

        areaImagem.add(coracao, BorderLayout.EAST);

        JPanel info = new JPanel();
        info.setBackground(Color.WHITE);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(15, 18, 0, 18));

        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblNome.setForeground(TEXTO);

        JLabel lblSexo = new JLabel("● " + sexo);
        lblSexo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSexo.setForeground(sexo.equals("Fêmea")
                ? new Color(236, 72, 153)
                : new Color(59, 130, 246));

        JLabel local = new JLabel("📍 Foz do Iguaçu, Centro");
        local.setFont(new Font("SansSerif", Font.PLAIN, 13));
        local.setForeground(CINZA);

        info.add(lblNome);
        info.add(Box.createVerticalStrut(10));
        info.add(lblSexo);
        info.add(Box.createVerticalStrut(6));
        info.add(local);

        card.add(areaImagem, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);

        return card;
    }

    private JLabel carregarImagem(String caminho, int largura, int altura) {
        try {
            java.net.URL url = getClass().getClassLoader().getResource(caminho);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(img));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel erro = new JLabel("🐾", SwingConstants.CENTER);
        erro.setFont(new Font("SansSerif", Font.PLAIN, 50));
        erro.setPreferredSize(new Dimension(largura, altura));
        erro.setOpaque(true);
        erro.setBackground(new Color(245, 246, 250));
        return erro;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AnimalView::new);
    }
}