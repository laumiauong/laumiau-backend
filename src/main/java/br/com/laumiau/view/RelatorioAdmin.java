package br.com.laumiau.view;

import laumiau.model.Adocoes;
import laumiau.model.Relatorio;
import laumiau.model.StatusAdocao;
import laumiau.service.AnimalService;
import laumiau.service.RelatorioService;
import laumiau.repository.AnimalRepository;
import laumiau.repository.AdocoesRepository;
import laumiau.infra.JPAUtil;

import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.List;

public class RelatorioAdmin extends JFrame {

    private EntityManager em = JPAUtil.getEntityManager();
    private RelatorioService relatorioService = new RelatorioService(em);
    private AnimalService animalService = new AnimalService(new AnimalRepository(em));

    private JLabel lblPetsDisponiveis, lblAdocoesMes, lblTotalAdotados, lblVacinados;
    private JPanel painelListaSolicitacoes;

    private static final Color LARANJA_BASE  = new Color(255, 153, 0);
    private static final Color LARANJA_DARK  = new Color(249, 115, 22);
    private static final Color FUNDO         = new Color(255, 251, 245);
    private static final Color TEXTO_DARK    = new Color(31, 41, 55);
    private static final Color BRANCO        = Color.WHITE;
    private static final Color BORDAS_LEVES  = new Color(230, 230, 230);

    public RelatorioAdmin() {
        setTitle("LauMiau - Dashboard Administrativa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 850);
        setLocationRelativeTo(null);

        getContentPane().setBackground(FUNDO);
        setLayout(new BorderLayout());

        add(criarNavbarAdmin(), BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(FUNDO);
        mainContent.setBorder(new EmptyBorder(20, 80, 40, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        JPanel tPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tPanel.setOpaque(false);
        JLabel lblT = new JLabel(
                "<html><div style='margin-bottom: 5px;'><b style='font-size: 22px;'>Dashboard Administrativo</b></div>"
                        + "<font color='#6b7280' size='4'>Visão geral das atividades da ONG</font></html>");
        tPanel.add(lblT);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        mainContent.add(tPanel, gbc);

        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 20, 0));
        cardsRow.setOpaque(false);

        lblPetsDisponiveis = new JLabel("--");
        lblAdocoesMes      = new JLabel("--");
        lblTotalAdotados   = new JLabel("--");
        lblVacinados       = new JLabel("--");

        cardsRow.add(new RoundedCard("Pets Disponíveis", lblPetsDisponiveis, "🧡"));
        cardsRow.add(new RoundedCard("Pets Adotados",    lblTotalAdotados,   "✅"));
        cardsRow.add(new RoundedCard("Total de Adoções", lblAdocoesMes,      "📋"));
        cardsRow.add(new RoundedCard("Pets Vacinados",   lblVacinados,       "💉"));

        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 35, 0);
        mainContent.add(cardsRow, gbc);

        gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0.65;
        gbc.insets = new Insets(0, 0, 0, 20);
        mainContent.add(criarPainelSolicitacoes(), gbc);

        gbc.gridx = 1; gbc.weightx = 0.35;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainContent.add(criarAcoesRapidas(), gbc);

        add(new JScrollPane(mainContent) {{
            setBorder(null);
            getViewport().setBackground(FUNDO);
        }}, BorderLayout.CENTER);

        atualizarDadosDoBanco();

        Timer timer = new Timer(10000, e -> atualizarDadosDoBanco());
        timer.start();
    }

    private JPanel criarNavbarAdmin() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(BRANCO);
        nav.setPreferredSize(new Dimension(1280, 68));
        nav.setBorder(new MatteBorder(0, 0, 1, 0, BORDAS_LEVES));

        JLabel logo = criarLogo();
        logo.setBorder(new EmptyBorder(0, 40, 0, 0));
        nav.add(logo, BorderLayout.WEST);

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 18));
        menu.setOpaque(false);

        JLabel dashboard = new JLabel("Dashboard");
        dashboard.setForeground(LARANJA_BASE);
        dashboard.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel animais = new JLabel("Animais");
        animais.setFont(new Font("SansSerif", Font.BOLD, 14));
        animais.setCursor(new Cursor(Cursor.HAND_CURSOR));
        animais.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    EntityManager emNovo = JPAUtil.getEntityManager();
                    AnimalService serviceFresco = new AnimalService(new AnimalRepository(emNovo));
                    new AnimaisCadastradosView(serviceFresco).setVisible(true);
                    dispose();
                });
            }
            @Override public void mouseEntered(MouseEvent e) { animais.setForeground(LARANJA_BASE); }
            @Override public void mouseExited(MouseEvent e)  { animais.setForeground(Color.BLACK); }
        });

        JLabel adotantes = new JLabel("Adotantes");
        adotantes.setFont(new Font("SansSerif", Font.BOLD, 14));
        adotantes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adotantes.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { adotantes.setForeground(LARANJA_BASE); }
            @Override public void mouseExited(MouseEvent e)  { adotantes.setForeground(Color.BLACK); }
        });

        menu.add(dashboard);
        menu.add(animais);
        menu.add(adotantes);
        nav.add(menu, BorderLayout.CENTER);

        JPanel sairContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 18));
        sairContainer.setOpaque(false);

        JButton btnSair = new JButton("Sair ➔") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(BORDAS_LEVES);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btnSair.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnSair.setForeground(LARANJA_BASE);
        btnSair.setPreferredSize(new Dimension(75, 30));
        btnSair.setContentAreaFilled(false);
        btnSair.setBorderPainted(false);
        btnSair.setFocusPainted(false);
        btnSair.addActionListener(e -> {
            EntityManager emNovo = JPAUtil.getEntityManager();
            AnimalService animalServiceNovo = new AnimalService(new AnimalRepository(emNovo));
            new AnimalView(animalServiceNovo).setVisible(true);
            dispose();
        });

        sairContainer.add(btnSair);
        nav.add(sairContainer, BorderLayout.EAST);

        return nav;
    }

    private JLabel criarLogo() {
        java.net.URL url = getClass().getResource("/img/logoLAUMIAU.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(160, 50, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        }
        JLabel logo = new JLabel("LAU 🐾 MIAU");
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setForeground(LARANJA_BASE);
        return logo;
    }


    private void carregarSolicitacoes() {
        try {
            EntityManager emLocal = JPAUtil.getEntityManager();
            List<Adocoes> adocoes = emLocal.createQuery(
                            "FROM Adocoes WHERE status = :status ORDER BY dataAdocao DESC",
                            Adocoes.class)
                    .setParameter("status", StatusAdocao.PENDENTE)
                    .setMaxResults(10)
                    .getResultList();

            SwingUtilities.invokeLater(() -> {
                painelListaSolicitacoes.removeAll();

                if (adocoes.isEmpty()) {
                    JLabel vazio = new JLabel("Nenhuma solicitação pendente.");
                    vazio.setForeground(Color.GRAY);
                    vazio.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    painelListaSolicitacoes.add(vazio);
                } else {
                    for (Adocoes a : adocoes) {
                        painelListaSolicitacoes.add(criarItemSolicitacao(a, emLocal));
                        painelListaSolicitacoes.add(Box.createVerticalStrut(10));
                    }
                }

                painelListaSolicitacoes.revalidate();
                painelListaSolicitacoes.repaint();
            });

        } catch (Exception e) {
            System.err.println("Erro ao carregar solicitações: " + e.getMessage());
        }
    }


    private JPanel criarItemSolicitacao(Adocoes adocao, EntityManager emLocal) {
        JPanel item = new JPanel(new BorderLayout(10, 0));
        item.setOpaque(false);
        item.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, BORDAS_LEVES),
                new EmptyBorder(10, 0, 10, 0)
        ));

        String nomeAnimal  = adocao.getAnimal() != null  ? adocao.getAnimal().getNome()   : "Animal";
        String nomeCliente = adocao.getCliente() != null ? adocao.getCliente().getNome()  : "Adotante";
        String data        = adocao.getDataAdocao() != null ? adocao.getDataAdocao().toString() : "";

        JLabel info = new JLabel(
                "<html><b>" + nomeAnimal + "</b>"
                        + "<br><font color='gray'>Por: " + nomeCliente + "</font>"
                        + "<br><font color='#aaaaaa' size='2'>" + data + "</font></html>"
        );
        info.setFont(new Font("SansSerif", Font.PLAIN, 13));
        item.add(info, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        botoes.setOpaque(false);

        JButton btnAprovar = new JButton("✔ Aprovar");
        btnAprovar.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnAprovar.setForeground(Color.WHITE);
        btnAprovar.setBackground(new Color(34, 197, 94));
        btnAprovar.setFocusPainted(false);
        btnAprovar.setBorderPainted(false);
        btnAprovar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAprovar.addActionListener(e -> processarAdocao(adocao.getIdAdocao(), true));

        JButton btnRecusar = new JButton("✖ Recusar");
        btnRecusar.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnRecusar.setForeground(Color.WHITE);
        btnRecusar.setBackground(new Color(239, 68, 68));
        btnRecusar.setFocusPainted(false);
        btnRecusar.setBorderPainted(false);
        btnRecusar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRecusar.addActionListener(e -> processarAdocao(adocao.getIdAdocao(), false));

        botoes.add(btnAprovar);
        botoes.add(btnRecusar);
        item.add(botoes, BorderLayout.EAST);

        return item;
    }


    private void processarAdocao(Long idAdocao, boolean aprovar) {
        try {
            EntityManager emLocal = JPAUtil.getEntityManager();
            Adocoes adocao = emLocal.find(Adocoes.class, idAdocao);

            if (adocao == null) {
                JOptionPane.showMessageDialog(this, "Solicitação não encontrada.");
                return;
            }

            emLocal.getTransaction().begin();
            if (aprovar) {
                adocao.aprovar(); // muda adoção para APROVADO e animal para ADOTADO
            } else {
                adocao.recusar(); // muda adoção para RECUSADO
            }
            emLocal.merge(adocao);
            emLocal.merge(adocao.getAnimal());
            emLocal.getTransaction().commit();
            emLocal.close();

            JOptionPane.showMessageDialog(this,
                    aprovar ? "Adoção aprovada com sucesso!" : "Solicitação recusada.",
                    aprovar ? "Aprovado" : "Recusado",
                    JOptionPane.INFORMATION_MESSAGE);


            atualizarDadosDoBanco();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao processar: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarDadosDoBanco() {
        try {
            if (em != null && em.isOpen()) em.close();
            em               = JPAUtil.getEntityManager();
            relatorioService = new RelatorioService(em);
            animalService    = new AnimalService(new AnimalRepository(em));

            Relatorio dados = relatorioService.obterRelatorioGeral();
            if (dados != null) {
                SwingUtilities.invokeLater(() -> {
                    lblPetsDisponiveis.setText(String.valueOf(dados.getTotalAnimaisDisponiveis()));
                    lblAdocoesMes.setText(String.valueOf(dados.getTotalAdocoes()));
                    lblTotalAdotados.setText(String.valueOf(dados.getTotalAnimaisAdotados()));
                    lblVacinados.setText(String.valueOf(dados.getTotalAnimaisVacinados()));
                });
            }
            carregarSolicitacoes();
        } catch (Exception e) {
            System.err.println("Erro ao atualizar dashboard: " + e.getMessage());
        }
    }

    private JPanel criarPainelSolicitacoes() {
        JPanel p = new RoundedPanel(25);
        p.setLayout(new BorderLayout());
        p.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel t = new JLabel("Solicitações Pendentes");
        t.setFont(new Font("SansSerif", Font.BOLD, 18));
        p.add(t, BorderLayout.NORTH);

        painelListaSolicitacoes = new JPanel();
        painelListaSolicitacoes.setLayout(new BoxLayout(painelListaSolicitacoes, BoxLayout.Y_AXIS));
        painelListaSolicitacoes.setOpaque(false);
        painelListaSolicitacoes.setBorder(new EmptyBorder(20, 0, 0, 0));
        p.add(painelListaSolicitacoes, BorderLayout.CENTER);

        return p;
    }

    private JPanel criarAcoesRapidas() {
        JPanel p = new JPanel(new BorderLayout(0, 20));
        p.setOpaque(false);
        JLabel t = new JLabel("AÇÕES RÁPIDAS", SwingConstants.CENTER);
        t.setFont(new Font("SansSerif", Font.BOLD, 12));
        p.add(t, BorderLayout.NORTH);

        JPanel botoes = new JPanel(new GridLayout(2, 1, 0, 15));
        botoes.setOpaque(false);

        OrangeButton btnCadastrar = new OrangeButton("➕  Cadastrar Novo Pet");
        btnCadastrar.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            EntityManager emNovo = JPAUtil.getEntityManager();
            AnimalService serviceFresco = new AnimalService(new AnimalRepository(emNovo));
            new CadastroAnimalView(serviceFresco).setVisible(true);
            dispose();
        }));

        OrangeButton btnGerenciar = new OrangeButton("📊  Gerenciar Animais");
        btnGerenciar.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            EntityManager emNovo = JPAUtil.getEntityManager();
            AnimalService serviceFresco = new AnimalService(new AnimalRepository(emNovo));
            new AnimaisCadastradosView(serviceFresco).setVisible(true);
            dispose();
        }));

        botoes.add(btnCadastrar);
        botoes.add(btnGerenciar);
        p.add(botoes, BorderLayout.CENTER);
        return p;
    }

    // ── Classes internas ──────────────────────────────────────────────────────

    class RoundedCard extends JPanel {
        public RoundedCard(String titulo, JLabel valor, String icon) {
            setLayout(new BorderLayout());
            setBackground(BRANCO);
            setPreferredSize(new Dimension(220, 130));
            setBorder(new EmptyBorder(20, 20, 20, 20));
            JLabel t = new JLabel(titulo);
            t.setForeground(new Color(150, 150, 150));
            valor.setFont(new Font("SansSerif", Font.BOLD, 32));
            add(t, BorderLayout.NORTH);
            add(valor, BorderLayout.CENTER);
            add(new JLabel(icon), BorderLayout.EAST);
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(BRANCO);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            g2.setColor(BORDAS_LEVES);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            g2.dispose();
        }
    }

    class OrangeButton extends JButton {
        public OrangeButton(String text) {
            super(text);
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setForeground(BRANCO);
            setContentAreaFilled(false);
            setBorder(new EmptyBorder(15, 20, 15, 20));
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint grad = new GradientPaint(0, 0, LARANJA_BASE, getWidth(), 0, LARANJA_DARK);
            g2.setPaint(grad);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    class RoundedPanel extends JPanel {
        private int r;
        public RoundedPanel(int radius) { this.r = radius; setOpaque(false); setBackground(BRANCO); }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);
            g2.setColor(BORDAS_LEVES);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, r, r);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RelatorioAdmin().setVisible(true));
    }
}