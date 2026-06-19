package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import laumiau.infra.JPAUtil;
import laumiau.model.Animal;
import laumiau.model.Cliente;
import laumiau.model.Endereco;
import laumiau.repository.*;
import laumiau.service.AdocoesService;
import laumiau.service.AnimalService;

public class AdocaoView extends JFrame {

    private final Animal        animalAdotando;
    private final Cliente       clienteLogado;
    private final AnimalService animalService;

    private JTextField txtNome, txtEmail, txtTelefone, txtCpf,
            txtDataNasc, txtProfissao, txtEndereco, txtCidade,
            txtEstado, txtCep, txtOutrosPets, txtMotivo;
    private JComboBox<String> comboMoradia, comboQuintal, comboTevePets;
    private JCheckBox checkTermo;

    private static final Color COR_FUNDO_CARD  = Color.WHITE;
    private static final Color COR_BORDA_SECAO = new Color(230, 218, 210);
    private static final Color COR_LABEL       = new Color(90, 90, 90);
    private static final Color COR_PLACEHOLDER = new Color(180, 180, 180);
    private static final Color COR_READONLY    = new Color(245, 245, 245);

    private static final String PH_TELEFONE   = "(11) 99999-9999";
    private static final String PH_CPF        = "000.000.000-00";
    private static final String PH_DATANASC   = "dd/mm/aaaa";
    private static final String PH_PROFISSAO  = "Sua profissão";
    private static final String PH_OUTROSPETS = "Conte sobre seus pets";
    private static final String PH_MOTIVO     = "Conte nos sua motivação para adotar";

    public AdocaoView(AnimalService animalService, Animal animalAdotando, Cliente clienteLogado) {
        this.animalService  = animalService;
        this.animalAdotando = animalAdotando;
        this.clienteLogado  = clienteLogado;

        setTitle("Formulário de Adoção - LauMiau");
        setSize(900, 950);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        criarTela();
    }

    private void criarTela() {
        setLayout(new BorderLayout());
        add(new NavbarPadrao("Home", animalService), BorderLayout.NORTH);

        JPanel fundo = new JPanel();
        fundo.setLayout(new BoxLayout(fundo, BoxLayout.Y_AXIS));
        fundo.setBackground(AppTheme.FUNDO);
        fundo.setBorder(new EmptyBorder(30, 0, 40, 0));

        JLabel titulo = new JLabel("Formulário de Adoção", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(new Color(50, 50, 50));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        fundo.add(titulo);

        JLabel subtitulo = new JLabel("Preencha os dados para adotar seu novo melhor amigo!", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(130, 130, 130));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        fundo.add(subtitulo);
        fundo.add(Box.createVerticalStrut(20));

        fundo.add(centralizar(criarBannerPet()));
        fundo.add(Box.createVerticalStrut(20));

        fundo.add(centralizar(criarCardDadosPessoais()));
        fundo.add(Box.createVerticalStrut(14));
        fundo.add(centralizar(criarCardEndereco()));
        fundo.add(Box.createVerticalStrut(14));
        fundo.add(centralizar(criarCardMoradia()));
        fundo.add(Box.createVerticalStrut(14));
        fundo.add(centralizar(criarCardHistoricoPets()));
        fundo.add(Box.createVerticalStrut(24));

        checkTermo = new JCheckBox("Li e concordo com os termos de adoção");
        checkTermo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        checkTermo.setBackground(AppTheme.FUNDO);
        fundo.add(centralizar(checkTermo));
        fundo.add(Box.createVerticalStrut(14));

        JButton btnEnviar = criarBotao("Enviar Solicitação");
        btnEnviar.addActionListener(e -> enviarSolicitacao());
        fundo.add(centralizar(btnEnviar));

        JScrollPane scroll = new JScrollPane(fundo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }

    private void enviarSolicitacao() {
        String erro = validarCampos();
        if (erro != null) { mostrarErro(erro); return; }

        int opcao = JOptionPane.showConfirmDialog(this,
                "Confirma a solicitação de adoção de " + animalAdotando.getNome() + "?\n" +
                        "Sua solicitação ficará com status PENDENTE até análise.",
                "Confirmar Adoção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcao != JOptionPane.YES_OPTION) return;

        try {
            AdocoesService service = new AdocoesService(
                    new AdocoesRepository(JPAUtil.getEntityManager()),
                    new AnimalRepository(JPAUtil.getEntityManager()),
                    new ClienteRepository(JPAUtil.getEntityManager()),
                    new SolicitacaoAdocaoRepository(JPAUtil.getEntityManager())
            );

            service.registrarAdocao(
                    animalAdotando.getId(),
                    clienteLogado.getId(),
                    checkTermo.isSelected(),
                    valor(txtTelefone,  PH_TELEFONE),
                    valor(txtCpf,       PH_CPF),
                    valor(txtDataNasc,  PH_DATANASC),
                    valor(txtProfissao, PH_PROFISSAO),
                    (String) comboMoradia.getSelectedItem(),
                    (String) comboQuintal.getSelectedItem(),
                    (String) comboTevePets.getSelectedItem(),
                    valor(txtOutrosPets, PH_OUTROSPETS),
                    valor(txtMotivo,     PH_MOTIVO)
            );

            mostrarSucesso();

            AnimalService novoService = new AnimalService(
                    new AnimalRepository(JPAUtil.getEntityManager())
            );
            new AnimalView(novoService, clienteLogado).setVisible(true);
            dispose();

        } catch (RuntimeException ex) {
            mostrarErro("Erro ao registrar adoção:\n" + ex.getMessage());
        }
    }


    private JPanel criarCardDadosPessoais() {
        JPanel card = criarCard("Dados Pessoais");
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(COR_FUNDO_CARD);

        txtNome  = addCampoReadOnly(grid, "Nome Completo",  clienteLogado.getNome(),  0, 0, 2);
        txtEmail = addCampoReadOnly(grid, "E-mail",          clienteLogado.getEmail(), 0, 2, 2);

        txtTelefone  = addCampo(grid, "Telefone/WhatsApp*",  PH_TELEFONE,  1, 0, 1);
        txtCpf       = addCampo(grid, "CPF*",                PH_CPF,       1, 1, 1);
        txtDataNasc  = addCampo(grid, "Data de nascimento*", PH_DATANASC,  1, 2, 1);
        txtProfissao = addCampo(grid, "Profissão*",          PH_PROFISSAO, 2, 0, 4);

        fixarColunas(grid, 4);
        card.add(grid);
        return empacotar(card);
    }

    private JPanel criarCardEndereco() {
        JPanel card = criarCard("Endereço");
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(COR_FUNDO_CARD);

        Endereco end = clienteLogado.getEndereco();
        if (end != null) {
            txtEndereco = addCampoReadOnly(grid, "Endereço completo", end.getLogradouro(), 0, 0, 4);
            txtCidade   = addCampoReadOnly(grid, "Cidade",             end.getCidade(),     1, 0, 2);
            txtEstado   = addCampoReadOnly(grid, "Estado",             end.getEstado(),     1, 2, 1);
            txtCep      = addCampoReadOnly(grid, "CEP",                end.getCep(),        1, 3, 1);
        } else {
            txtEndereco = addCampo(grid, "Endereço completo*", "Rua, número, complemento", 0, 0, 4);
            txtCidade   = addCampo(grid, "Cidade*",             "Sua cidade",               1, 0, 2);
            txtEstado   = addCampo(grid, "Estado*",             "Ex: SP",                   1, 2, 1);
            txtCep      = addCampo(grid, "CEP*",                "00000-000",                1, 3, 1);
        }

        fixarColunas(grid, 4);
        card.add(grid);
        return empacotar(card);
    }

    private JPanel criarCardMoradia() {
        JPanel card = criarCard("Informações sobre sua moradia");
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(COR_FUNDO_CARD);

        comboMoradia = new JComboBox<>(new String[]{"Selecione", "Casa", "Apartamento"});
        comboQuintal = new JComboBox<>(new String[]{"Selecione", "Sim", "Não"});
        addCombo(grid, "Tipo de moradia*",             comboMoradia, 0, 0, 4);
        addCombo(grid, "Possui quintal/área externa?", comboQuintal, 1, 0, 4);

        fixarColunas(grid, 4);
        card.add(grid);
        return empacotar(card);
    }

    private JPanel criarCardHistoricoPets() {
        JPanel card = criarCard("Histórico com Pets");
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(COR_FUNDO_CARD);

        comboTevePets = new JComboBox<>(new String[]{"Selecione", "Sim", "Não"});
        addCombo(grid,  "Você já teve pets antes?*",       comboTevePets, 0, 0, 4);
        txtOutrosPets = addCampoTexto(grid, "Possui outros pets atualmente?*", PH_OUTROSPETS, 1, 0, 4, 3);
        txtMotivo     = addCampoTexto(grid, "Nos conte o motivo da adoção!*",  PH_MOTIVO,     2, 0, 4, 3);

        fixarColunas(grid, 4);
        card.add(grid);
        return empacotar(card);
    }


    private String validarCampos() {
        if (vazio(txtTelefone,  PH_TELEFONE))  return "Preencha o campo: Telefone/WhatsApp";
        if (vazio(txtCpf,       PH_CPF))       return "Preencha o campo: CPF";
        if (vazio(txtDataNasc,  PH_DATANASC))  return "Preencha o campo: Data de Nascimento";
        if (vazio(txtProfissao, PH_PROFISSAO)) return "Preencha o campo: Profissão";

        if (clienteLogado.getEndereco() == null) {
            if (vazio(txtEndereco, "Rua, número, complemento")) return "Preencha o campo: Endereço";
            if (vazio(txtCidade,   "Sua cidade"))               return "Preencha o campo: Cidade";
            if (vazio(txtEstado,   "Ex: SP"))                   return "Preencha o campo: Estado";
            if (vazio(txtCep,      "00000-000"))                return "Preencha o campo: CEP";
        }

        if (comboMoradia.getSelectedIndex()  == 0) return "Selecione o tipo de moradia.";
        if (comboQuintal.getSelectedIndex()  == 0) return "Informe se possui quintal/área externa.";
        if (comboTevePets.getSelectedIndex() == 0) return "Informe se já teve pets antes.";
        if (vazio(txtMotivo, PH_MOTIVO))           return "Conte-nos o motivo da adoção.";
        if (!checkTermo.isSelected())              return "Você precisa aceitar os termos de adoção.";

        return null;
    }

    private boolean vazio(JTextField txt, String placeholder) {
        String v = txt.getText().trim();
        return v.isEmpty() || v.equals(placeholder);
    }

    private String valor(JTextField txt, String placeholder) {
        String v = txt.getText().trim();
        return (v.isEmpty() || v.equals(placeholder)) ? null : v;
    }


    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarSucesso() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel emoji = new JLabel("🧡", SwingConstants.CENTER);
        emoji.setFont(new Font("SansSerif", Font.PLAIN, 40));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel linha1 = new JLabel("Solicitação enviada com sucesso!", SwingConstants.CENTER);
        linha1.setFont(new Font("SansSerif", Font.BOLD, 16));
        linha1.setForeground(AppTheme.LARANJA);
        linha1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel linha2 = new JLabel(
                "<html><div style='text-align:center'>Sua solicitação de adoção de <b>" +
                        animalAdotando.getNome() +
                        "</b> está em análise.<br>Entraremos em contato em breve!</div></html>",
                SwingConstants.CENTER);
        linha2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        linha2.setForeground(new Color(80, 80, 80));
        linha2.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(emoji);
        p.add(Box.createVerticalStrut(10));
        p.add(linha1);
        p.add(Box.createVerticalStrut(8));
        p.add(linha2);

        JOptionPane.showMessageDialog(this, p, "Adoção Solicitada!", JOptionPane.PLAIN_MESSAGE);
    }

    private JTextField addCampo(JPanel grid, String label, String placeholder,
                                int row, int col, int colspan) {
        GridBagConstraints gbc = baseGbc(row, col, colspan);
        gbc.gridy = row * 2;
        grid.add(makeLbl(label), gbc);

        JTextField txt = new JTextField();
        estilizarCampo(txt, placeholder);
        gbc.gridy  = row * 2 + 1;
        gbc.insets = new Insets(4, 6, 12, 6);
        grid.add(txt, gbc);
        return txt;
    }

    private JTextField addCampoReadOnly(JPanel grid, String label, String valor,
                                        int row, int col, int colspan) {
        GridBagConstraints gbc = baseGbc(row, col, colspan);
        gbc.gridy = row * 2;
        grid.add(makeLbl(label + "  🔒"), gbc);

        JTextField txt = new JTextField(valor != null ? valor : "");
        txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txt.setPreferredSize(new Dimension(0, 36));
        txt.setEditable(false);
        txt.setBackground(COR_READONLY);
        txt.setForeground(new Color(100, 100, 100));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        gbc.gridy  = row * 2 + 1;
        gbc.insets = new Insets(4, 6, 12, 6);
        grid.add(txt, gbc);
        return txt;
    }

    private JTextField addCampoTexto(JPanel grid, String label, String placeholder,
                                     int row, int col, int colspan, int linhas) {
        JTextField txt = addCampo(grid, label, placeholder, row, col, colspan);
        txt.setPreferredSize(new Dimension(0, linhas * 28));
        return txt;
    }

    private void addCombo(JPanel grid, String label, JComboBox<String> combo,
                          int row, int col, int colspan) {
        GridBagConstraints gbc = baseGbc(row, col, colspan);
        gbc.gridy = row * 2;
        grid.add(makeLbl(label), gbc);

        estilizarCombo(combo);
        gbc.gridy  = row * 2 + 1;
        gbc.insets = new Insets(4, 6, 12, 6);
        grid.add(combo, gbc);
    }

    private GridBagConstraints baseGbc(int row, int col, int colspan) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx     = col;
        gbc.gridwidth = colspan;
        gbc.fill      = GridBagConstraints.HORIZONTAL;
        gbc.weightx   = colspan;
        gbc.anchor    = GridBagConstraints.WEST;
        gbc.insets    = new Insets(0, 6, 2, 6);
        return gbc;
    }

    private void fixarColunas(JPanel grid, int numCols) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy   = 9999;
        gbc.weightx = 1.0;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        for (int c = 0; c < numCols; c++) {
            gbc.gridx = c;
            grid.add(Box.createHorizontalStrut(1), gbc);
        }
    }

    private JLabel makeLbl(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setForeground(COR_LABEL);
        return lbl;
    }

    private void estilizarCampo(JTextField txt, String placeholder) {
        txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txt.setPreferredSize(new Dimension(0, 36));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        txt.setText(placeholder);
        txt.setForeground(COR_PLACEHOLDER);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txt.getText().equals(placeholder)) {
                    txt.setText("");
                    txt.setForeground(new Color(30, 30, 30));
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txt.getText().isEmpty()) {
                    txt.setText(placeholder);
                    txt.setForeground(COR_PLACEHOLDER);
                }
            }
        });
    }

    private void estilizarCombo(JComboBox<String> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        combo.setPreferredSize(new Dimension(0, 36));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
    }


    private JPanel criarBannerPet() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBackground(new Color(255, 245, 240));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.LARANJA, 1, true),
                new EmptyBorder(14, 20, 14, 20)));
        p.setMaximumSize(new Dimension(580, 80));

        JLabel icone = new JLabel("🧡");
        icone.setFont(new Font("SansSerif", Font.PLAIN, 28));
        icone.setBackground(AppTheme.LARANJA);
        icone.setOpaque(true);
        icone.setBorder(new EmptyBorder(8, 10, 8, 10));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(new Color(255, 245, 240));

        JLabel nomePet = new JLabel(animalAdotando.getNome());
        nomePet.setFont(new Font("SansSerif", Font.BOLD, 15));
        nomePet.setForeground(new Color(50, 50, 50));

        JLabel subtxt = new JLabel("Você está adotando esse pet!");
        subtxt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtxt.setForeground(new Color(120, 120, 120));

        textos.add(nomePet);
        textos.add(subtxt);

        p.add(icone);
        p.add(Box.createHorizontalStrut(14));
        p.add(textos);
        return p;
    }

    private JPanel criarCard(String titulo) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COR_FUNDO_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                new AppTheme.RoundedBorder(16, COR_BORDA_SECAO),
                new EmptyBorder(20, 24, 20, 24)));

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(new Color(100, 100, 100));
        lbl.setBorder(new EmptyBorder(0, 0, 14, 0));
        card.add(lbl);
        return card;
    }

    private JPanel empacotar(JPanel card) {
        card.setMaximumSize(new Dimension(580, Integer.MAX_VALUE));
        card.setPreferredSize(new Dimension(580, card.getPreferredSize().height));
        return card;
    }

    private JPanel centralizar(Component c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.setBackground(AppTheme.FUNDO);
        p.add(c);
        return p;
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(AppTheme.LARANJA);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(320, 46));
        btn.setMaximumSize(new Dimension(320, 46));
        return btn;
    }
}