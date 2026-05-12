package laumiau.view;

import laumiau.model.Animal;
import laumiau.model.Porte;
import laumiau.model.Sexo;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalView extends JFrame {

    private static final Color LARANJA = new Color(249, 115, 22);
    private static final Color LARANJA_CLARO = new Color(255, 237, 213);
    private static final Color LARANJA_BG = new Color(255, 247, 237);
    private static final Color TEXTO_ESCURO = new Color(17, 24, 39);
    private static final Color TEXTO_CINZA = new Color(107, 114, 128);
    private static final Color BORDA = new Color(240, 240, 240);
    private static final Color ROSA = new Color(236, 72, 153);
    private static final Color AZUL = new Color(59, 130, 246);

    private String filtroEspecie = "Todos";
    private final List<Animal> todosAnimais = new ArrayList<>();
    private JPanel gridAnimais;

    public AnimalView() {
        carregarDadosDemo();
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    private void carregarDadosDemo() {
        todosAnimais.add(new Animal("Fofuxo", "Gato", "Vira-lata", 12, Sexo.MACHO, false, Porte.MEDIO));
        todosAnimais.add(new Animal("Princesa", "Gato", "Persa", 6, Sexo.FEMEA, false, Porte.MEDIO));
        todosAnimais.add(new Animal("Bonitão", "Gato", "Siamês", 24, Sexo.MACHO, true, Porte.MEDIO));
        todosAnimais.add(new Animal("Bebê", "Gato", "Vira-lata", 3, Sexo.FEMEA, false, Porte.PEQUENO));
        todosAnimais.add(new Animal("Charmosa", "Gato", "Angora", 18, Sexo.FEMEA, true, Porte.MEDIO));
        todosAnimais.add(new Animal("Rabinho", "Cachorro", "Dachshund", 5, Sexo.MACHO, false, Porte.PEQUENO));
        todosAnimais.add(new Animal("Banquela", "Cachorro", "SRD", 8, Sexo.MACHO, false, Porte.MEDIO));
        todosAnimais.add(new Animal("Preciosa", "Gato", "Ragdoll", 14, Sexo.FEMEA, true, Porte.MEDIO));
        todosAnimais.add(new Animal("Renê", "Gato", "Vira-lata", 9, Sexo.MACHO, false, Porte.PEQUENO));
        todosAnimais.add(new Animal("Doguinho", "Cachorro", "SRD", 10, Sexo.MACHO, true, Porte.PEQUENO));
    }

    private void configurarJanela() {
        setTitle("LAU & MIAU - Animais");
        setSize(1200, 750);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void construirInterface() {
        add(criarTopo(), BorderLayout.NORTH);
        add(criarAreaConteudo(), BorderLayout.CENTER);
        add(criarFooter(), BorderLayout.SOUTH);
    }

    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Color.WHITE);
        topo.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, BORDA),
                new EmptyBorder(14, 50, 14, 50)
        ));

        JLabel logo = carregarImagem("img/img.png", 180, 70);

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        menu.setBackground(Color.WHITE);

        menu.add(criarNavItem("Home", false));
        menu.add(criarNavItem("Animais", true));
        menu.add(criarNavItem("Sobre nós", false));

        JButton btnAdmin = criarBotaoAdmin();
        btnAdmin.addActionListener(e -> abrirAdmin());

        topo.add(logo, BorderLayout.WEST);
        topo.add(menu, BorderLayout.CENTER);
        topo.add(btnAdmin, BorderLayout.EAST);

        return topo;
    }

    private JLabel criarNavItem(String texto, boolean ativo) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", ativo ? Font.BOLD : Font.PLAIN, 14));
        lbl.setBorder(new EmptyBorder(10, 22, 10, 22));
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (ativo) {
            lbl.setForeground(LARANJA);
            lbl.setOpaque(true);
            lbl.setBackground(LARANJA_CLARO);
        } else {
            lbl.setForeground(TEXTO_CINZA);
        }

        return lbl;
    }

    private JButton criarBotaoAdmin() {
        JButton btn = new JButton("👤 Admin") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(LARANJA_CLARO);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(LARANJA);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    private JScrollPane criarAreaConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(Color.WHITE);
        conteudo.setBorder(new EmptyBorder(36, 50, 36, 50));

        conteudo.add(criarCabecalhoConteudo(), BorderLayout.NORTH);

        gridAnimais = new JPanel();
        gridAnimais.setBackground(Color.WHITE);
        atualizarGrid();

        JPanel wrapGrid = new JPanel(new BorderLayout());
        wrapGrid.setBackground(Color.WHITE);
        wrapGrid.setBorder(new EmptyBorder(30, 0, 0, 0));
        wrapGrid.add(gridAnimais, BorderLayout.CENTER);

        conteudo.add(wrapGrid, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getViewport().setBackground(Color.WHITE);

        return scroll;
    }

    private JPanel criarCabecalhoConteudo() {
        JPanel painel = new JPanel(new BorderLayout(0, 16));
        painel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Confira todos os pets e encontre um amigo!");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(TEXTO_ESCURO);

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filtros.setBackground(Color.WHITE);
        filtros.setBorder(new EmptyBorder(10, 0, 0, 0));

        filtros.add(criarBotaoFiltro("🐾 Todos", "Todos"));
        filtros.add(criarBotaoFiltro("🐱 Gatos", "Gato"));
        filtros.add(criarBotaoFiltro("🐶 Cães", "Cachorro"));

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(filtros, BorderLayout.CENTER);

        return painel;
    }

    private JButton criarBotaoFiltro(String texto, String especie) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean selecionado = filtroEspecie.equals(especie);
                g2.setColor(selecionado ? LARANJA_CLARO : new Color(245, 245, 245));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(filtroEspecie.equals(especie) ? LARANJA : TEXTO_CINZA);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            filtroEspecie = especie;
            atualizarGrid();
        });

        return btn;
    }

    private void atualizarGrid() {
        gridAnimais.removeAll();

        List<Animal> filtrados = todosAnimais.stream()
                .filter(a -> filtroEspecie.equals("Todos") || a.getEspecie().equals(filtroEspecie))
                .collect(Collectors.toList());

        gridAnimais.setLayout(new GridLayout(0, 5, 20, 20));

        for (Animal animal : filtrados) {
            String imagem = buscarImagemPorNome(animal.getNome());
            gridAnimais.add(criarCardAnimal(animal, imagem));
        }

        gridAnimais.revalidate();
        gridAnimais.repaint();
    }

    private String buscarImagemPorNome(String nome) {
        switch (nome) {
            case "Fofuxo":
                return "img/gato1.png";
            case "Princesa":
                return "img/gato2.png";
            case "Bonitão":
                return "img/gato3.png";
            case "Bebê":
                return "img/gato4.png";
            case "Charmosa":
                return "img/gato6.png";
            case "Rabinho":
                return "img/cachorro1.png";
            case "Banquela":
                return "img/cachorro2.png";
            case "Preciosa":
                return "img/gato8.png";
            case "Renê":
                return "img/gato9.png";
            case "Doguinho":
                return "img/gato10.png";
            default:
                return "";
        }
    }

    private JPanel criarCardAnimal(Animal animal, String caminhoImagem) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setBorder(new CompoundBorder(
                new LineBorder(BORDA, 1, true),
                new EmptyBorder(0, 0, 12, 0)
        ));

        JLabel foto = carregarImagem(caminhoImagem, 190, 140);
        card.add(foto, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);
        info.setBorder(new EmptyBorder(12, 14, 0, 14));

        JLabel lblNome = new JLabel(animal.getNome());
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblNome.setForeground(TEXTO_ESCURO);

        boolean femea = animal.getSexo() == Sexo.FEMEA;

        JLabel lblSexo = new JLabel("● " + (femea ? "Fêmea" : "Macho"));
        lblSexo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSexo.setForeground(femea ? ROSA : AZUL);

        JLabel lblLocal = new JLabel("📍 Foz do Iguaçu, Centro");
        lblLocal.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLocal.setForeground(TEXTO_CINZA);

        info.add(lblNome);
        info.add(Box.createVerticalStrut(8));
        info.add(lblSexo);
        info.add(Box.createVerticalStrut(6));
        info.add(lblLocal);

        card.add(info, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(
                        AnimalView.this,
                        "Nome: " + animal.getNome()
                                + "\nEspécie: " + animal.getEspecie()
                                + "\nRaça: " + animal.getRaca()
                                + "\nIdade: " + animal.getIdade() + " meses"
                                + "\nSexo: " + animal.getSexo()
                                + "\nPorte: " + animal.getPorte()
                                + "\nVacinado: " + (animal.isVacinado() ? "Sim" : "Não"),
                        "Detalhes do Animal",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        return card;
    }

    private JPanel criarFooter() {
        JPanel footer = new JPanel(new GridLayout(1, 4, 30, 0));
        footer.setBackground(LARANJA_BG);
        footer.setBorder(new EmptyBorder(30, 50, 30, 50));

        footer.add(carregarImagem("img/img.png", 140, 55));
        footer.add(criarColunaFooter("Links Úteis", new String[]{"Home", "Animais", "Sobre nós"}));
        footer.add(criarColunaFooter("Contato", new String[]{"laumiau@ong.com", "(45) 99999-0000"}));
        footer.add(criarColunaFooter("Novidades", new String[]{"Siga nas redes sociais", "@laumiau_ong"}));

        return footer;
    }

    private JPanel criarColunaFooter(String titulo, String[] itens) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(LARANJA_BG);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(TEXTO_ESCURO);

        col.add(lbl);
        col.add(Box.createVerticalStrut(8));

        for (String item : itens) {
            JLabel i = new JLabel(item);
            i.setFont(new Font("SansSerif", Font.PLAIN, 12));
            i.setForeground(TEXTO_CINZA);
            col.add(i);
            col.add(Box.createVerticalStrut(4));
        }

        return col;
    }

    private void abrirAdmin() {
        JOptionPane.showMessageDialog(
                this,
                "Área administrativa de animais.\nAqui depois pode abrir a tela de cadastro.",
                "Admin",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JLabel carregarImagem(String caminho, int largura, int altura) {
        try {
            java.net.URL url = getClass().getClassLoader().getResource(caminho);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

                JLabel lbl = new JLabel(new ImageIcon(img));
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setPreferredSize(new Dimension(largura, altura));
                return lbl;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel erro = new JLabel("🐾", SwingConstants.CENTER);
        erro.setFont(new Font("SansSerif", Font.PLAIN, 35));
        erro.setOpaque(true);
        erro.setBackground(new Color(243, 244, 246));
        erro.setPreferredSize(new Dimension(largura, altura));
        return erro;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AnimalView::new);
    }
}