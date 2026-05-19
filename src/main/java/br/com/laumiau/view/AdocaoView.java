package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import laumiau.infra.JPAUtil;
import laumiau.model.Animal;
import laumiau.model.Cliente;
import laumiau.model.Adocoes;
import laumiau.repository.AdocoesRepository;
import laumiau.repository.AnimalRepository;
import laumiau.repository.ClienteRepository;
import laumiau.service.AdocoesService;
import laumiau.service.AnimalService;
import jakarta.persistence.EntityManager;

public class AdocaoView extends JFrame {

    private Animal animalAdotando;
    private Cliente clienteLogado;
    private final String nomeAdotante;
    private JCheckBox checkTermo;
    private AnimalService animalService;
    private String nomeAnimalAuxiliar;


    public AdocaoView(AnimalService animalService, Animal animalAdotando, Cliente clienteLogado) {
        this.animalService = animalService;
        this.animalAdotando = animalAdotando;
        this.clienteLogado = clienteLogado;
        this.nomeAdotante = clienteLogado != null ? clienteLogado.getNome() : "Adotante";
        this.nomeAnimalAuxiliar = animalAdotando != null ? animalAdotando.getNome() : "";

        setTitle("Adoção - LauMiau");
        setSize(1000, 750);
        setMinimumSize(new Dimension(760, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        criarTela();
    }


    public AdocaoView(AnimalService animalService, Animal animalAdotando, String nomeAdotante) {
        this.animalService = animalService;
        this.animalAdotando = animalAdotando;
        this.nomeAdotante = nomeAdotante;
        this.clienteLogado = null;
        this.nomeAnimalAuxiliar = animalAdotando != null ? animalAdotando.getNome() : "";

        setTitle("Adoção - LauMiau");
        setSize(1000, 750);
        setMinimumSize(new Dimension(760, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        criarTela();
    }


    public AdocaoView(AnimalService animalService, String nomeAnimal, String nomeAdotante) {
        this.animalService = animalService;
        this.nomeAnimalAuxiliar = nomeAnimal;
        this.nomeAdotante = nomeAdotante;
        this.clienteLogado = null;

        try {
            if (animalService != null && nomeAnimal != null) {
                this.animalAdotando = animalService.listarTodos().stream()
                        .filter(a -> a.getNome() != null && a.getNome().equalsIgnoreCase(nomeAnimal))
                        .findFirst()
                        .orElse(null);
                if (this.animalAdotando != null) {
                    this.nomeAnimalAuxiliar = this.animalAdotando.getNome();
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível carregar o objeto Animal pelo nome: " + e.getMessage());
        }

        setTitle("Adoção - LauMiau");
        setSize(1000, 750);
        setMinimumSize(new Dimension(760, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        criarTela();
    }

    private void criarTela() {
        setLayout(new BorderLayout());
        add(new NavbarPadrao("Home", animalService), BorderLayout.NORTH);

        JPanel fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(AppTheme.FUNDO);

        JPanel card = new JPanel();
        card.setBackground(AppTheme.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(450, 620));
        card.setBorder(BorderFactory.createCompoundBorder(
                new AppTheme.RoundedBorder(28, AppTheme.BORDA),
                new EmptyBorder(26, 34, 24, 34)
        ));

        JLabel titulo = new JLabel("Confirmar adoção");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        titulo.setForeground(AppTheme.TEXTO_DARK);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Confira os dados abaixo antes de finalizar.");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(AppTheme.CINZA_TEXTO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(titulo);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(24));

        card.add(labelCampo("Animal"));
        card.add(caixaInfoEmoji("🐱", nomeAnimalAuxiliar, AppTheme.LARANJA, Color.WHITE));
        card.add(Box.createVerticalStrut(14));

        card.add(labelCampo("Adotante"));
        card.add(caixaInfoEmoji("👤", nomeAdotante, AppTheme.LARANJA, Color.WHITE));
        card.add(Box.createVerticalStrut(14));

        Color COR_VERDE = new Color(0x22C55E);
        Color COR_VERDE_FUNDO = new Color(0xEAFBF1);

        card.add(labelCampo("Status Atual"));
        card.add(caixaInfoEmoji("●", "Disponível para adoção", COR_VERDE, COR_VERDE_FUNDO));
        card.add(Box.createVerticalStrut(20));

        JButton btnLinkForms = new JButton("🔗 Abrir Formulário de Adoção (Google Forms)");
        btnLinkForms.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnLinkForms.setForeground(AppTheme.LARANJA);
        btnLinkForms.setContentAreaFilled(false);
        btnLinkForms.setBorderPainted(false);
        btnLinkForms.setFocusPainted(false);
        btnLinkForms.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLinkForms.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLinkForms.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://docs.google.com/forms/d/e/1FAIpQLScBtBRVicxq4TFRQNfS0BoXHNV7O59H78_GOFFHiYd_u7RCtA/viewform"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Não foi possível abrir o link automaticamente.");
            }
        });
        card.add(btnLinkForms);
        card.add(Box.createVerticalStrut(10));

        JTextArea texto = new JTextArea(
                "Ao confirmar esta adoção, você declara que o formulário do Google Forms foi respondido, "
                        + "o termo foi assinado e que o adotante se responsabiliza pelos cuidados, segurança, "
                        + "saúde e bem-estar do animal."
        );
        texto.setEditable(false);
        texto.setFocusable(false);
        texto.setOpaque(false);
        texto.setBorder(null);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("SansSerif", Font.PLAIN, 13));
        texto.setForeground(AppTheme.CINZA_TEXTO);
        texto.setMaximumSize(new Dimension(360, 72));
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(texto);
        card.add(Box.createVerticalStrut(14));

        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        checkPanel.setOpaque(false);
        checkPanel.setMaximumSize(new Dimension(360, 30));
        checkTermo = new JCheckBox("Confirmo que o formulário foi respondido e assinado");
        checkTermo.setOpaque(false);
        checkTermo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        checkTermo.setForeground(AppTheme.TEXTO_DARK);
        checkTermo.setFocusPainted(false);
        checkPanel.add(checkTermo);
        card.add(checkPanel);
        card.add(Box.createVerticalStrut(18));

        RoundedButton btnConfirmar = new RoundedButton("Confirmar adoção");
        btnConfirmar.setPreferredSize(new Dimension(360, 45));
        btnConfirmar.setMaximumSize(new Dimension(360, 45));
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmar.addActionListener(e -> confirmarAdocao());
        card.add(btnConfirmar);
        card.add(Box.createVerticalStrut(14));

        JLabel ou = new JLabel("ou");
        ou.setFont(new Font("SansSerif", Font.BOLD, 12));
        ou.setForeground(AppTheme.CINZA_TEXTO);
        ou.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(ou);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setForeground(AppTheme.LARANJA);
        btnVoltar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.addActionListener(e -> {
            dispose();
            if (animalService != null) {
                new AnimalView(animalService, clienteLogado).setVisible(true);
            }
        });

        card.add(Box.createVerticalStrut(6));
        card.add(btnVoltar);

        fundo.add(card);
        add(fundo, BorderLayout.CENTER);
    }

    private JLabel labelCampo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(AppTheme.TEXTO_DARK);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setMaximumSize(new Dimension(360, 22));
        return label;
    }

    private JPanel caixaInfoEmoji(String icone, String texto, Color corIcone, Color fundo) {
        JPanel caixa = new JPanel(new BorderLayout(10, 0));
        caixa.setBackground(fundo);
        caixa.setMaximumSize(new Dimension(360, 38));
        caixa.setPreferredSize(new Dimension(360, 38));
        caixa.setBorder(BorderFactory.createCompoundBorder(
                new AppTheme.RoundedBorder(10, AppTheme.BORDA),
                new EmptyBorder(0, 12, 0, 12)
        ));
        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblIcone.setForeground(corIcone);
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTexto.setForeground(AppTheme.TEXTO_DARK);
        caixa.add(lblIcone, BorderLayout.WEST);
        caixa.add(lblTexto, BorderLayout.CENTER);
        return caixa;
    }

    private void confirmarAdocao() {
        if (!checkTermo.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Você precisa preencher o formulário e marcar a caixa de confirmação.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (animalAdotando == null || animalAdotando.getId() == null) {
            JOptionPane.showMessageDialog(this,
                    "Erro: animal sem ID válido. Volte e tente novamente pela lista.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            EntityManager emAdocao = JPAUtil.getEntityManager();
            AdocoesRepository adocoesRepo = new AdocoesRepository(emAdocao);
            AnimalRepository animalRepo = new AnimalRepository(emAdocao);
            ClienteRepository clienteRepo = new ClienteRepository(emAdocao);
            AdocoesService adocoesService = new AdocoesService(adocoesRepo, animalRepo, clienteRepo);

            if (clienteLogado != null) {

                adocoesService.registrarAdocao(
                        animalAdotando.getId(),
                        clienteLogado.getId(),
                        true
                );
            } else {
                // Fallback sem cliente logado: só atualiza status do animal
                animalService.adotar(animalAdotando.getId());
            }

            emAdocao.close();

            JOptionPane.showMessageDialog(this,
                    "Vamos analisar sua solicitação e enviaremos o retorno.",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            if (animalService != null) {
                new AnimalView(animalService, clienteLogado).setVisible(true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao processar a adoção: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class RoundedButton extends JButton {
        public RoundedButton(String texto) {
            super(texto);
            setFont(new Font("SansSerif", Font.BOLD, 15));
            setForeground(AppTheme.BRANCO);
            setBackground(AppTheme.LARANJA);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { setBackground(AppTheme.LARANJA_HOVER); repaint(); }
                @Override public void mouseExited(MouseEvent e)  { setBackground(AppTheme.LARANJA); repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.setColor(getForeground());
            g2.setFont(getFont());
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
    }
}