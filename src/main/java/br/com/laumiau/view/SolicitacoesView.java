package br.com.laumiau.view;

import jakarta.persistence.EntityManager;
import laumiau.infra.JPAUtil;
import laumiau.model.Adocoes;
import laumiau.model.StatusAdocao;
import laumiau.model.StatusAnimal;
import laumiau.model.SolicitacaoAdocao;
import laumiau.repository.AnimalRepository;
import laumiau.repository.SolicitacaoAdocaoRepository;
import laumiau.service.AnimalService;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SolicitacoesView extends JFrame {

    private static final Color LARANJA_BASE  = new Color(255, 153, 0);
    private static final Color FUNDO         = new Color(255, 251, 245);
    private static final Color TEXTO_DARK    = new Color(31, 41, 55);
    private static final Color BRANCO        = Color.WHITE;
    private static final Color BORDAS_LEVES  = new Color(230, 230, 230);

    private JPanel gridSolicitacoes;

    public SolicitacoesView() {
        setTitle("LauMiau - Formulários de Adoção");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 850);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FUNDO);
        setLayout(new BorderLayout());

        add(criarNavbarAdmin(), BorderLayout.NORTH);
        add(criarConteudo(), BorderLayout.CENTER);
    }

    private JPanel criarNavbarAdmin() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(BRANCO);
        nav.setPreferredSize(new Dimension(1280, 68));
        nav.setBorder(new MatteBorder(0, 0, 1, 0, BORDAS_LEVES));

        java.net.URL url = getClass().getResource("/img/logoLAUMIAU.png");
        JLabel logo;
        if (url != null) {
            Image img = new ImageIcon(url).getImage().getScaledInstance(160, 50, Image.SCALE_SMOOTH);
            logo = new JLabel(new ImageIcon(img));
        } else {
            logo = new JLabel("LAU 🐾 MIAU");
            logo.setFont(new Font("SansSerif", Font.BOLD, 20));
            logo.setForeground(LARANJA_BASE);
        }
        logo.setBorder(new EmptyBorder(0, 40, 0, 0));
        nav.add(logo, BorderLayout.WEST);

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 18));
        menu.setOpaque(false);

        JLabel dashboard = new JLabel("Dashboard");
        dashboard.setFont(new Font("SansSerif", Font.BOLD, 14));
        dashboard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dashboard.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> { new RelatorioAdmin().setVisible(true); dispose(); });
            }
            @Override public void mouseEntered(MouseEvent e) { dashboard.setForeground(LARANJA_BASE); }
            @Override public void mouseExited(MouseEvent e)  { dashboard.setForeground(Color.BLACK);  }
        });

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

        JLabel solicitacoes = new JLabel("Solicitações");
        solicitacoes.setForeground(LARANJA_BASE); // Menu ativo
        solicitacoes.setFont(new Font("SansSerif", Font.BOLD, 14));

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

    private JScrollPane criarConteudo() {
        JPanel fundo = new JPanel(new BorderLayout());
        fundo.setBackground(FUNDO);
        fundo.setBorder(new EmptyBorder(35, 45, 35, 45));

        JLabel titulo = new JLabel("Formulários de Adoção Pendentes");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(TEXTO_DARK);
        titulo.setBorder(new EmptyBorder(0, 0, 25, 0));
        fundo.add(titulo, BorderLayout.NORTH);

        gridSolicitacoes = new JPanel(new GridLayout(0, 3, 25, 25));
        gridSolicitacoes.setOpaque(false);
        carregarSolicitacoes();

        fundo.add(gridSolicitacoes, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(fundo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(FUNDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private void carregarSolicitacoes() {
        gridSolicitacoes.removeAll();
        EntityManager emLocal = null;
        try {
            emLocal = JPAUtil.getEntityManager();
            List<Adocoes> pendentes = emLocal.createQuery(
                            "SELECT a FROM Adocoes a WHERE a.status = :s ORDER BY a.idAdocao DESC", Adocoes.class)
                    .setParameter("s", StatusAdocao.PENDENTE)
                    .getResultList();

            if (pendentes.isEmpty()) {
                gridSolicitacoes.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel lblVazio = new JLabel("Não há formulários pendentes para análise no momento.");
                lblVazio.setFont(new Font("SansSerif", Font.PLAIN, 16));
                lblVazio.setForeground(Color.GRAY);
                gridSolicitacoes.add(lblVazio);
            } else {
                gridSolicitacoes.setLayout(new GridLayout(0, 3, 25, 25));
                for (Adocoes adocao : pendentes) {
                    gridSolicitacoes.add(criarCard(adocao));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (emLocal != null) emLocal.close();
            gridSolicitacoes.revalidate();
            gridSolicitacoes.repaint();
        }
    }

    private JPanel criarCard(Adocoes adocao) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BRANCO);
        card.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(BORDAS_LEVES, 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        String nomeAnimal = adocao.getAnimal() != null ? adocao.getAnimal().getNome() : "Animal";
        String nomeCliente = adocao.getCliente() != null ? adocao.getCliente().getNome() : "Adotante";

        JLabel lblAnimal = new JLabel("Pet: " + nomeAnimal);
        lblAnimal.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblAnimal.setForeground(TEXTO_DARK);

        JLabel lblAdotante = new JLabel("Adotante: " + nomeCliente);
        lblAdotante.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblAdotante.setForeground(Color.GRAY);

        JPanel info = new JPanel(new GridLayout(2, 1, 0, 5));
        info.setOpaque(false);
        info.add(lblAnimal);
        info.add(lblAdotante);
        info.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnAnalisar = new JButton("👁 Analisar Formulário") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btnAnalisar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnAnalisar.setForeground(BRANCO);
        btnAnalisar.setBackground(LARANJA_BASE);
        btnAnalisar.setFocusPainted(false);
        btnAnalisar.setBorderPainted(false);
        btnAnalisar.setContentAreaFilled(false);
        btnAnalisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAnalisar.setPreferredSize(new Dimension(0, 40));

        btnAnalisar.addActionListener(e -> analisarAdocao(adocao.getIdAdocao()));

        card.add(info, BorderLayout.CENTER);
        card.add(btnAnalisar, BorderLayout.SOUTH);

        return card;
    }

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

            carregarSolicitacoes(); // Atualiza a tela

        } catch (Exception ex) {
            if (emLocal != null && emLocal.getTransaction().isActive()) emLocal.getTransaction().rollback();
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (emLocal != null) emLocal.close();
        }
    }
}