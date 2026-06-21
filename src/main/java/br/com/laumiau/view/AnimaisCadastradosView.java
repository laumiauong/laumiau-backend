package br.com.laumiau.view;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import laumiau.infra.JPAUtil;
import laumiau.controller.LoginController;
import laumiau.model.Animal;
import laumiau.model.Cliente;
import laumiau.model.Sexo;
import laumiau.model.StatusAnimal;
import laumiau.model.SolicitacaoAdocao;
import laumiau.repository.AnimalRepository;
import laumiau.repository.SolicitacaoAdocaoRepository;
import laumiau.repository.UsuarioRepository;
import laumiau.service.AnimalService;
import laumiau.service.UsuarioService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AnimaisCadastradosView extends JFrame {

    private final Color LARANJA = new Color(255, 107, 43);
    private final Color FUNDO   = new Color(253, 247, 242);
    private final Color TEXTO   = new Color(15, 23, 42);
    private final Color CINZA   = new Color(160, 170, 185);

    private AnimalService animalService;
    private UsuarioService usuarioService;
    private Cliente clienteLogado;
    private boolean isAdmin;

    private JPanel grid;
    private JTextField pesquisa;

    private final String PLACEHOLDER = "Buscar por nome ou ID";

    public AnimaisCadastradosView(AnimalService animalService) {
        this(animalService, null, null, false);
    }

    public AnimaisCadastradosView(AnimalService animalService, UsuarioService usuarioService) {
        this(animalService, usuarioService, null, false);
    }

    public AnimaisCadastradosView(AnimalService animalService, Cliente clienteLogado, boolean isAdmin) {
        this(animalService, null, clienteLogado, isAdmin);
    }

    public AnimaisCadastradosView(AnimalService animalService, UsuarioService usuarioService, Cliente clienteLogado, boolean isAdmin) {
        this.animalService = animalService;
        this.usuarioService = usuarioService;
        this.clienteLogado = clienteLogado;
        this.isAdmin = isAdmin;

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
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Color.WHITE);
        topo.setBorder(new EmptyBorder(18, 35, 18, 35));

        JLabel titulo = new JLabel("←  Animais Cadastrados");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(TEXTO);
        titulo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        titulo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                EntityManager emNovo = JPAUtil.getEntityManager();
                AnimalService novoService = new AnimalService(new AnimalRepository(emNovo), new SolicitacaoAdocaoRepository(emNovo));
                UsuarioService novoUsuarioService = usuarioService != null
                        ? usuarioService
                        : new UsuarioService(new UsuarioRepository(emNovo));
                new AnimalView(novoService, novoUsuarioService, clienteLogado);
            }
        });

        if (isAdmin) {
            JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            adminPanel.setOpaque(false);

            JLabel adminTexto = new JLabel("Admin");
            adminTexto.setFont(new Font("SansSerif", Font.PLAIN, 15));
            adminTexto.setForeground(TEXTO);

            JLabel avatar = new JLabel("A", SwingConstants.CENTER);
            avatar.setOpaque(true);
            avatar.setBackground(LARANJA);
            avatar.setForeground(Color.WHITE);
            avatar.setFont(new Font("SansSerif", Font.BOLD, 16));
            avatar.setPreferredSize(new Dimension(38, 38));

            adminPanel.add(adminTexto);
            adminPanel.add(avatar);
            topo.add(adminPanel, BorderLayout.EAST);
        }

        topo.add(titulo, BorderLayout.WEST);
        return topo;
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

        barra.add(pesquisa, BorderLayout.WEST);

        if (isAdmin) {
            JButton btnNovo = new RoundedButton("+  Novo Cadastro", LARANJA, Color.WHITE);
            btnNovo.setFont(new Font("SansSerif", Font.BOLD, 16));
            btnNovo.addActionListener(e -> new CadastroAnimalView(animalService));
            barra.add(btnNovo, BorderLayout.EAST);
        }

        grid = new JPanel(new GridLayout(0, 5, 24, 24));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(30, 0, 0, 0));

        carregarAnimais("");

        pesquisa.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { carregarAnimais(pesquisa.getText()); }
            @Override public void removeUpdate(DocumentEvent e)  { carregarAnimais(pesquisa.getText()); }
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

        try {
            java.util.List<Animal> listaAnimais = animalService.listarTodos();

            if (listaAnimais == null || listaAnimais.isEmpty()) {
                JLabel lblAviso = new JLabel("Nenhum animal cadastrado no banco de dados.", SwingConstants.CENTER);
                lblAviso.setFont(new Font("SansSerif", Font.PLAIN, 16));
                lblAviso.setForeground(CINZA);
                grid.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
                grid.add(lblAviso);
            } else {
                grid.setLayout(new GridLayout(0, 5, 24, 24));

                for (Animal animal : listaAnimais) {
                    String nomeAnimal = animal.getNome();
                    boolean correspondeNome = nomeAnimal != null && nomeAnimal.toLowerCase().contains(texto);
                    boolean correspondeId   = String.valueOf(animal.getId()).contains(texto);

                    if (texto.isEmpty() || correspondeNome || correspondeId) {
                        grid.add(criarCard(animal));
                    }
                }
            }
        } catch (Exception e) {
            JLabel lblErro = new JLabel("Erro ao conectar com o banco: " + e.getMessage(), SwingConstants.CENTER);
            lblErro.setForeground(Color.RED);
            grid.add(lblErro);
            e.printStackTrace();
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

        carregarImagemSegura(animal.getCaminhoFoto(), foto, 250, 200);

        JPanel topoFoto = new JPanel(new BorderLayout());
        topoFoto.setPreferredSize(new Dimension(250, 200));
        topoFoto.setOpaque(false);

        JPanel acoes = new JPanel();
        acoes.setOpaque(false);
        acoes.setLayout(new BoxLayout(acoes, BoxLayout.Y_AXIS));
        acoes.setBorder(new EmptyBorder(10, 0, 10, 10));

        JPanel linhaExcluir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        linhaExcluir.setOpaque(false);

        if (isAdmin) {
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

            JLabel verForm = new JLabel("👁");
            verForm.setFont(new Font("SansSerif", Font.BOLD, 20));
            verForm.setForeground(LARANJA);
            verForm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            verForm.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    e.consume();
                    SolicitacaoAdocao sol = animalService.buscarSolicitacaoDoAnimal(animal.getId());
                    if (sol != null) {
                        JOptionPane.showMessageDialog(null,
                                "CPF: " + sol.getCpf() + "\n" +
                                        "Profissão: " + sol.getProfissao() + "\n" +
                                        "Moradia: " + sol.getTipoMoradia() + "\n" +
                                        "Possui Quintal: " + sol.getPossuiQuintal() + "\n" +
                                        "Já teve pets: " + sol.getTevePetsAntes() + "\n" +
                                        "Outros pets: " + sol.getOutrosPets() + "\n" +
                                        "Motivo: " + sol.getMotivoAdocao(),
                                "Formulário de Adoção", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum formulário vinculado.");
                    }
                }
            });
            linhaExcluir.add(verForm);

            JLabel excluir = new JLabel("🗑");
            excluir.setFont(new Font("SansSerif", Font.BOLD, 18));
            excluir.setForeground(new Color(255, 90, 100));
            excluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            excluir.setAlignmentX(Component.CENTER_ALIGNMENT);

            excluir.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    e.consume();

                    Object[] options = {"Cancelar Adoção", "Excluir Animal", "Voltar"};
                    int escolha = JOptionPane.showOptionDialog(null,
                            "O que deseja fazer com " + animal.getNome() + "?\n(Cancelar a adoção faz o pet voltar para a aba de adoções)",
                            "Gerenciar Pet",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                    if (escolha == 0) {
                        EntityManager emLocal = null;
                        try {
                            emLocal = JPAUtil.getEntityManager();
                            emLocal.getTransaction().begin();

                            Animal animalBd = emLocal.find(Animal.class, animal.getId());
                            if(animalBd != null) {
                                animalBd.setStatus(StatusAnimal.DISPONIVEL);
                                emLocal.createQuery("DELETE FROM Adocoes a WHERE a.animal.id = :animalId")
                                        .setParameter("animalId", animal.getId())
                                        .executeUpdate();
                            }

                            emLocal.getTransaction().commit();


                            animal.setStatus(StatusAnimal.DISPONIVEL);

                            carregarAnimais(pesquisa.getText());
                            JOptionPane.showMessageDialog(null, "Adoção cancelada! O pet já está de volta à lista principal.");
                        } catch (Exception erro) {
                            if (emLocal != null && emLocal.getTransaction().isActive()) emLocal.getTransaction().rollback();
                            JOptionPane.showMessageDialog(null, "Erro ao cancelar adoção: " + erro.getMessage());
                        } finally {
                            if (emLocal != null && emLocal.isOpen()) emLocal.close();
                        }

                    } else if (escolha == 1) {
                        try {
                            animalService.remover(animal.getId());
                            carregarAnimais(pesquisa.getText());
                            JOptionPane.showMessageDialog(null, "Animal excluído com sucesso!");
                        } catch (Exception erro) {
                            JOptionPane.showMessageDialog(null, "Não foi possível excluir. Verifique se ele possui dependências.");
                        }
                    }
                }
            });

            acoes.add(editar);
            excluir.setFont(new Font("SansSerif", Font.BOLD, 17));
            linhaExcluir.add(excluir);
        }

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

        JLabel nome = new JLabel(animal.getNome() != null ? animal.getNome() : "Sem nome");
        nome.setFont(new Font("SansSerif", Font.BOLD, 18));
        nome.setForeground(TEXTO);

        Color corSexo = animal.getSexo() == Sexo.FEMEA
                ? new Color(236, 72, 153)
                : new Color(59, 130, 246);

        JLabel sexo = new JLabel("● " + (animal.getSexo() == Sexo.FEMEA ? "Fêmea" : "Macho"));
        sexo.setForeground(corSexo);
        sexo.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel local = new JLabel("📍 Foz do Iguaçu, Centro");
        local.setForeground(CINZA);
        local.setFont(new Font("SansSerif", Font.PLAIN, 12));

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
            public void mouseClicked(MouseEvent e) {
                abrirDetalhes(animal);
            }
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

        carregarImagemSegura(animal.getCaminhoFoto(), imagem, 560, 620);

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

        JPanel vacina = new RoundedPanel(
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
                textoOuPadrao(animal.getDescricao(), "Sem descrição cadastrada.")
        );
        descricao.setEditable(false);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setOpaque(false);
        descricao.setFont(new Font("SansSerif", Font.PLAIN, 17));
        descricao.setForeground(new Color(70, 70, 80));

        JButton adotar = new RoundedButton("❤ Quero adotar", LARANJA, Color.WHITE);
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
                UsuarioService service = usuarioService;
                if (service == null) {
                    service = new UsuarioService(new UsuarioRepository(JPAUtil.getEntityManager()));
                }
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

    private void carregarImagemSegura(String caminho, JLabel label, int largura, int altura) {
        label.setIcon(null);
        if (caminho != null && !caminho.isBlank()) {
            try {
                java.net.URL url = getClass().getClassLoader().getResource(caminho);
                ImageIcon icon;
                if (url != null) {
                    icon = new ImageIcon(url);
                } else {
                    icon = new ImageIcon(caminho);
                }

                if (icon.getIconWidth() > 0) {
                    Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));
                    label.setText("");
                    return;
                }
            } catch (Exception e) {
                System.err.println("Aviso: Falha ao carregar a imagem do animal.");
            }
        }

        label.setText("🐾");
        label.setFont(new Font("SansSerif", Font.PLAIN, largura >= 250 ? 56 : 46));
        label.setForeground(CINZA);
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("laumiau");
        EntityManager em = emf.createEntityManager();
        AnimalRepository repository = new AnimalRepository(em);
        AnimalService service = new AnimalService(repository, new SolicitacaoAdocaoRepository(em));
        SwingUtilities.invokeLater(() -> new AnimaisCadastradosView(service));
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bg;

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
        private final Color bg;

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
