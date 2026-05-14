package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RelatorioAdmin extends JFrame {

    private static final Color LARANJA     = new Color(255, 153, 0);
    private static final Color FUNDO       = new Color(255, 251, 245);
    private static final Color TEXTO_DARK  = new Color(31, 41, 55);
    private static final Color TEXTO_LIGHT = new Color(156, 163, 175);
    private static final Color BRANCO      = Color.WHITE;
    private static final Color COR_BORDA   = new Color(243, 244, 246);

    public RelatorioAdmin() {
        setTitle("LauMiau - Relatório Administrativo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 850);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FUNDO);
        setLayout(new BorderLayout());

        add(criarHeader(), BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout(0, 30));
        mainContent.setBackground(FUNDO);
        mainContent.setBorder(new EmptyBorder(30, 80, 40, 80));

        // Título atualizado conforme pedido
        JPanel painelTexto = new JPanel(new GridLayout(2, 1, 0, 5));
        painelTexto.setOpaque(false);
        JLabel lblTitulo = new JLabel("Relatório Administrativo");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setForeground(TEXTO_DARK);
        JLabel lblSub = new JLabel("Visão geral das atividades da ONG");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblSub.setForeground(TEXTO_LIGHT);
        painelTexto.add(lblTitulo);
        painelTexto.add(lblSub);
        mainContent.add(painelTexto, BorderLayout.NORTH);

        JPanel areaConteudo = new JPanel(new BorderLayout(0, 35));
        areaConteudo.setOpaque(false);
        areaConteudo.add(criarLinhaStats(), BorderLayout.NORTH);
        areaConteudo.add(criarGridCorpo(), BorderLayout.CENTER);

        mainContent.add(areaConteudo, BorderLayout.CENTER);
        add(new JScrollPane(mainContent) {{ 
            setBorder(null); 
            getViewport().setBackground(FUNDO);
        }}, BorderLayout.CENTER);
    }

    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BRANCO);
        header.setPreferredSize(new Dimension(getWidth(), 75));
        header.setBorder(new MatteBorder(0, 0, 1, 0, new Color(245, 245, 245)));

        JLabel logo = new JLabel(" LAU 🐾 MIAU");
        logo.setFont(new Font("SansSerif", Font.BOLD, 24));
        logo.setForeground(LARANJA);
        logo.setBorder(new EmptyBorder(0, 80, 0, 0));
        header.add(logo, BorderLayout.WEST);

        JPanel navCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        navCentral.setOpaque(false);
        navCentral.add(criarBotaoNav("Relatório", true));
        navCentral.add(criarBotaoNav("Animais", false));
        navCentral.add(criarBotaoNav("Adotantes", false));
        header.add(navCentral, BorderLayout.CENTER);

        JButton btnSair = new JButton("Sair →") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.setColor(new Color(255, 235, 210));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnSair.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSair.setForeground(LARANJA);
        btnSair.setContentAreaFilled(false); btnSair.setBorderPainted(false);
        btnSair.setPreferredSize(new Dimension(85, 35));

        JPanel pDireito = new JPanel(new FlowLayout(FlowLayout.RIGHT, 80, 20));
        pDireito.setOpaque(false);
        pDireito.add(btnSair);
        header.add(pDireito, BorderLayout.EAST);

        return header;
    }

    private JPanel criarLinhaStats() {
        JPanel p = new JPanel(new GridLayout(1, 4, 20, 0));
        p.setOpaque(false);
        p.add(new CardStat("Pets Disponíveis", "44", "🧡", new Color(255, 145, 0)));
        p.add(new CardStat("Solicitações", "12", "🔔", new Color(255, 200, 0)));
        p.add(new CardStat("Adoções (Mês)", "12", "✅", new Color(74, 222, 128)));
        p.add(new CardStat("Novos Pets", "8", "➕", new Color(59, 130, 246)));
        return p;
    }

    private JPanel criarGridCorpo() {
        JPanel p = new JPanel(new BorderLayout(30, 0));
        p.setOpaque(false);

        // Solicitações
        JPanel painelSolicitacoes = new PainelArredondado(35, true);
        painelSolicitacoes.setBackground(BRANCO);
        painelSolicitacoes.setLayout(new BorderLayout());
        painelSolicitacoes.setBorder(new EmptyBorder(25, 30, 30, 30));

        JLabel tit = new JLabel("Solicitações Recentes");
        tit.setFont(new Font("SansSerif", Font.BOLD, 19));
        painelSolicitacoes.add(tit, BorderLayout.NORTH);

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setOpaque(false);
        lista.add(Box.createVerticalStrut(20));
        lista.add(new ItemSolicitacao("Mel", "Maria Silva", "Pendente", new Color(254, 243, 199), new Color(180, 83, 9)));
        lista.add(Box.createVerticalStrut(15));
        lista.add(new ItemSolicitacao("Bella", "João Santos", "Aprovado", new Color(220, 252, 231), new Color(21, 128, 61)));
        
        painelSolicitacoes.add(lista, BorderLayout.CENTER);

        // Coluna Direita (Ações e Atividades)
        JPanel colunaDireita = new JPanel(new BorderLayout(0, 25));
        colunaDireita.setOpaque(false);
        colunaDireita.setPreferredSize(new Dimension(340, 0));

        JPanel acoes = new PainelArredondado(35, true);
        acoes.setBackground(BRANCO);
        acoes.setLayout(new GridLayout(2, 1, 0, 15));
        acoes.setBorder(new EmptyBorder(30, 25, 30, 25));
        acoes.add(new BotaoAcao("Cadastrar Novo Pet", LARANJA));
        acoes.add(new BotaoAcao("Gerenciar Animais", new Color(251, 110, 30)));

        colunaDireita.add(acoes, BorderLayout.NORTH);
        p.add(painelSolicitacoes, BorderLayout.CENTER);
        p.add(colunaDireita, BorderLayout.EAST);

        return p;
    }

    // --- Classes de Componentes ---

    class PainelArredondado extends JPanel {
        private int raio;
        private boolean comBorda;
        public PainelArredondado(int r, boolean b) { this.raio = r; this.comBorda = b; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, raio, raio);
            if(comBorda) { g2.setColor(COR_BORDA); g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, raio, raio); }
            g2.dispose();
        }
    }

    class CardStat extends PainelArredondado {
        public CardStat(String t, String v, String icon, Color iconColor) {
            super(35, true); setBackground(BRANCO);
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(20, 22, 20, 22));
            JLabel tit = new JLabel(t); tit.setFont(new Font("SansSerif", Font.BOLD, 13)); tit.setForeground(TEXTO_LIGHT);
            JLabel val = new JLabel(v); val.setFont(new Font("SansSerif", Font.BOLD, 38)); val.setForeground(TEXTO_DARK);
            JLabel ico = new JLabel(icon); ico.setForeground(iconColor);
            add(tit, BorderLayout.NORTH); add(val, BorderLayout.CENTER); add(ico, BorderLayout.EAST);
        }
    }

    class ItemSolicitacao extends PainelArredondado {
        public ItemSolicitacao(String pet, String dono, String status, Color bgTag, Color fgTag) {
            super(25, true); setBackground(new Color(254, 254, 254));
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(12, 20, 12, 20));
            
            JPanel pInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0)); pInfo.setOpaque(false);
            JLabel nome = new JLabel(pet); nome.setFont(new Font("SansSerif", Font.BOLD, 16));
            JLabel tag = new JLabel(" " + status + " ") {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(bgTag); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2.dispose(); super.paintComponent(g);
                }
            };
            tag.setFont(new Font("SansSerif", Font.BOLD, 10)); tag.setForeground(fgTag);
            pInfo.add(nome); pInfo.add(tag);

            JLabel sub = new JLabel("Por: " + dono); sub.setForeground(TEXTO_LIGHT); sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
            add(pInfo, BorderLayout.NORTH); add(sub, BorderLayout.CENTER); add(new JLabel("〉"), BorderLayout.EAST);
        }
    }

    class BotaoAcao extends JButton {
        public BotaoAcao(String t, Color c) {
            super(t); setFocusPainted(false); setBorderPainted(false); setContentAreaFilled(false);
            setForeground(BRANCO); setFont(new Font("SansSerif", Font.BOLD, 14));
            setPreferredSize(new Dimension(280, 50)); setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(LARANJA); // Mantendo o laranja sólido da marca
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose(); super.paintComponent(g);
        }
    }

    private JButton criarBotaoNav(String texto, boolean ativo) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                if (ativo) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 240, 220)); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", ativo ? Font.BOLD : Font.BOLD, 14));
        btn.setForeground(ativo ? LARANJA : new Color(75, 85, 99));
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RelatorioAdmin().setVisible(true));
    }
}