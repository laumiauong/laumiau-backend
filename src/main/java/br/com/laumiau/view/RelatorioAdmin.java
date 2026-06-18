package br.com.laumiau.view;

import laumiau.model.Adocoes;
import laumiau.model.Relatorio;
import laumiau.model.StatusAdocao;
import laumiau.model.StatusAnimal;
import laumiau.model.SolicitacaoAdocao;
import laumiau.service.AnimalService;
import laumiau.service.RelatorioService;
import laumiau.repository.AnimalRepository;
import laumiau.repository.SolicitacaoAdocaoRepository;
import laumiau.infra.JPAUtil;

import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.List;
import java.util.ArrayList;

public class RelatorioAdmin extends JFrame {

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
                "<html><div style='margin-bottom:5px;'><b style='font-size:22px;'>Dashboard Administrativo</b></div>"
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

        cardsRow.add(new RoundedCard("Pets Disponíveis",      lblPetsDisponiveis, "🧡"));
        cardsRow.add(new RoundedCard("Pets Adotados",         lblTotalAdotados,   "✅"));
        cardsRow.add(new RoundedCard("Total de Solicitações", lblAdocoesMes,      "📋"));
        cardsRow.add(new RoundedCard("Pets Vacinados",        lblVacinados,       "💉"));

        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 35, 0);
        mainContent.add(cardsRow, gbc);

        gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0.65;
        gbc.insets = new Insets(0, 0, 0, 20);
        mainContent.add(criarPainelSolicitacoes(), gbc);

        gbc.gridx = 1; gbc.weightx = 0.35;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainContent.add(criarAcoesRapidas(), gbc);

        JScrollPane scroll = new JScrollPane(mainContent);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(FUNDO);
        add(scroll, BorderLayout.CENTER);

        atualizarDadosDoBanco();
        new Timer(10_000, e -> atualizarDadosDoBanco()).start();
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
        animais.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        animais.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    EntityManager emNovo = JPAUtil.getEntityManager();
                    new AnimaisCadastradosView(new AnimalService(new AnimalRepository(emNovo), new SolicitacaoAdocaoRepository(emNovo)), null, true).setVisible(true);
                    dispose();
                });
            }
            @Override public void mouseEntered(MouseEvent e) { animais.setForeground(LARANJA_BASE); }
            @Override public void mouseExited(MouseEvent e)  { animais.setForeground(Color.BLACK);  }
        });

        // AQUI ESTÁ A MUDANÇA: Agora o menu aponta para a página de Solicitações nova!
        JLabel solicitacoes = new JLabel("Solicitações");
        solicitacoes.setFont(new Font("SansSerif", Font.BOLD, 14));
        solicitacoes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        solicitacoes.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> { new SolicitacoesView().setVisible(true); dispose(); });
            }
            @Override public void mouseEntered(MouseEvent e) { solicitacoes.setForeground(LARANJA_BASE); }
            @Override public void mouseExited(MouseEvent e)  { solicitacoes.setForeground(Color.BLACK);  }
        });

        menu.add(dashboard);
        menu.add(animais);
        menu.add(solicitacoes);
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
            new AnimalView(new AnimalService(new AnimalRepository(emNovo), new SolicitacaoAdocaoRepository(emNovo))).setVisible(true);
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

    public void atualizarDadosDoBanco() {
        EntityManager emLocal = null;
        try {
            emLocal = JPAUtil.getEntityManager();

            RelatorioService rs = new RelatorioService(emLocal);
            Relatorio dados = rs.obterRelatorioGeral();
            if (dados != null) {
                final Relatorio d = dados;
                SwingUtilities.invokeLater(() -> {
                    lblPetsDisponiveis.setText(String.valueOf(d.getTotalAnimaisDisponiveis()));
                    lblTotalAdotados  .setText(String.valueOf(d.getTotalAnimaisAdotados()));
                    lblAdocoesMes     .setText(String.valueOf(d.getTotalAdocoes()));
                    lblVacinados      .setText(String.valueOf(d.getTotalAnimaisVacinados()));
                });
            }

            final List<Adocoes> pendentes = emLocal
                    .createQuery(
                            "SELECT a FROM Adocoes a " +
                                    "LEFT JOIN FETCH a.animal " +
                                    "LEFT JOIN FETCH a.cliente " +
                                    "WHERE a.status = :s " +
                                    "ORDER BY a.idAdocao DESC",
                            Adocoes.class)
                    .setParameter("s", StatusAdocao.PENDENTE)
                    .setMaxResults(20)
                    .getResultList();

            SwingUtilities.invokeLater(() -> renderizarSolicitacoes(pendentes));

        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> renderizarSolicitacoes(new ArrayList<>()));
        } finally {
            if (emLocal != null && emLocal.isOpen()) emLocal.close();
        }
    }

    private void renderizarSolicitacoes(List<Adocoes> lista) {
        painelListaSolicitacoes.removeAll();

        if (lista.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma solicitação pendente.");
            vazio.setForeground(Color.GRAY);
            vazio.setFont(new Font("SansSerif", Font.PLAIN, 14));
            painelListaSolicitacoes.add(vazio);
        } else {
            for (Adocoes a : lista) {
                painelListaSolicitacoes.add(criarItemSolicitacao(a));
                painelListaSolicitacoes.add(Box.createVerticalStrut(10));
            }
        }

        painelListaSolicitacoes.revalidate();
        painelListaSolicitacoes.repaint();
    }

    private JPanel criarItemSolicitacao(Adocoes adocao) {
        JPanel item = new JPanel(new BorderLayout(10, 0));
        item.setOpaque(false);
        item.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, BORDAS_LEVES),
                new EmptyBorder(12, 0, 12, 0)
        ));

        String nomeAnimal  = adocao.getAnimal()  != null ? adocao.getAnimal().getNome()  : "Animal";
        String nomeCliente = adocao.getCliente() != null ? adocao.getCliente().getNome() : "Adotante";

        JLabel info = new JLabel(
                "<html><b style='font-size:13px;'>" + nomeAnimal + "</b>"
                        + "<br><font color='gray'>Por: " + nomeCliente + "</font></html>"
        );
        item.add(info, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.setOpaque(false);

        // AQUI ESTÁ A MUDANÇA: Um botão único de "Analisar" no painel principal
        JButton btnAnalisar = botaoAcao("👁  Analisar", LARANJA_BASE);
        btnAnalisar.addActionListener(e -> analisarAdocao(adocao.getIdAdocao()));

        botoes.add(btnAnalisar);
        item.add(botoes, BorderLayout.EAST);

        return item;
    }

    private JButton botaoAcao(String texto, Color cor) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 11));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 30));
        return btn;
    }

    // AQUI ESTÁ A MUDANÇA: Método que abre o formulário antes de aprovar
    private void analisarAdocao(Long idAdocao) {
        EntityManager emLocal = null;
        try {
            emLocal = JPAUtil.getEntityManager();
            Adocoes adocao = emLocal.find(Adocoes.class, idAdocao);
            SolicitacaoAdocao sol = new SolicitacaoAdocaoRepository(emLocal).buscarPorAdocaoId(idAdocao);

            if (adocao == null || sol == null) {
                JOptionPane.showMessageDialog(this, "Respostas do formulário não encontradas para esta solicitação.");
                return;
            }

            JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
            painel.add(new JLabel("<html><b>Animal:</b> " + adocao.getAnimal().getNome() + "</html>"));
            painel.add(new JLabel("<html><b>Adotante:</b> " + adocao.getCliente().getNome() + "</html>"));
            painel.add(new JLabel("<html><b>CPF:</b> " + sol.getCpf() + "</html>"));
            painel.add(new JLabel("<html><b>Telefone:</b> " + sol.getTelefone() + "</html>"));
            painel.add(new JLabel("<html><b>Profissão:</b> " + sol.getProfissao() + "</html>"));
            painel.add(new JLabel("<html><b>Moradia:</b> " + sol.getTipoMoradia() + " (" + sol.getPossuiQuintal() + " quintal)</html>"));
            painel.add(new JLabel("<html><b>Teve Pets antes:</b> " + sol.getTevePetsAntes() + "</html>"));
            painel.add(new JLabel("<html><b>Outros Pets:</b> " + sol.getOutrosPets() + "</html>"));

            JLabel lblMotivo = new JLabel("<html><br><b>Motivo da Adoção:</b><br><p style='width:350px;'>" + sol.getMotivoAdocao() + "</p></html>");

            JPanel container = new JPanel(new BorderLayout());
            container.add(painel, BorderLayout.CENTER);
            container.add(lblMotivo, BorderLayout.SOUTH);

            Object[] options = {"✔ Aprovar Adoção", "✖ Recusar Adoção", "Voltar"};
            int escolha = JOptionPane.showOptionDialog(this, container, "Análise do Formulário",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (escolha == 0) {
                processarAdocao(idAdocao, true);
            } else if (escolha == 1) {
                processarAdocao(idAdocao, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (emLocal != null) emLocal.close();
        }
    }

    private void processarAdocao(Long idAdocao, boolean aprovar) {
        EntityManager emLocal = null;
        try {
            emLocal = JPAUtil.getEntityManager();
            Adocoes adocao = emLocal.find(Adocoes.class, idAdocao);

            emLocal.getTransaction().begin();

            if (aprovar) {
                adocao.aprovar();
                adocao.getAnimal().setStatus(StatusAnimal.ADOTADO);
                emLocal.merge(adocao.getAnimal());
            } else {
                adocao.recusar();
            }

            emLocal.merge(adocao);
            emLocal.getTransaction().commit();

            JOptionPane.showMessageDialog(this,
                    aprovar ? "Adoção aprovada com sucesso!" : "Solicitação recusada.",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            atualizarDadosDoBanco();

        } catch (Exception ex) {
            if (emLocal != null && emLocal.getTransaction().isActive()) emLocal.getTransaction().rollback();
            JOptionPane.showMessageDialog(this, "Erro ao processar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (emLocal != null) emLocal.close();
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
            new CadastroAnimalView(new AnimalService(new AnimalRepository(emNovo), new SolicitacaoAdocaoRepository(emNovo))).setVisible(true);
            dispose();
        }));

        OrangeButton btnGerenciar = new OrangeButton("📊  Gerenciar Animais");
        btnGerenciar.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            EntityManager emNovo = JPAUtil.getEntityManager();
            new AnimaisCadastradosView(new AnimalService(new AnimalRepository(emNovo), new SolicitacaoAdocaoRepository(emNovo)), null, true).setVisible(true);
            dispose();
        }));

        botoes.add(btnCadastrar);
        botoes.add(btnGerenciar);
        p.add(botoes, BorderLayout.CENTER);
        return p;
    }

    class RoundedCard extends JPanel {
        public RoundedCard(String titulo, JLabel valor, String icon) {
            setLayout(new BorderLayout());
            setBackground(BRANCO);
            setPreferredSize(new Dimension(220, 130));
            setBorder(new EmptyBorder(20, 20, 20, 20));
            JLabel t = new JLabel(titulo);
            t.setForeground(new Color(150, 150, 150));
            valor.setFont(new Font("SansSerif", Font.BOLD, 32));
            add(t,             BorderLayout.NORTH);
            add(valor,         BorderLayout.CENTER);
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
            g2.setPaint(new GradientPaint(0, 0, LARANJA_BASE, getWidth(), 0, LARANJA_DARK));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    class RoundedPanel extends JPanel {
        private final int r;
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