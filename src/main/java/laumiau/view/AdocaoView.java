package laumiau.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import laumiau.view.TutorView.RoundedBorder;
import laumiau.view.TutorView.RoundedButton;

public class AdocaoView extends JFrame {

    // ── Paleta ───────────────────────────────────────────────────────────────
    private static final Color COR_FUNDO   = new Color(0xFFF8F2);
    private static final Color COR_LARANJA = new Color(0xFF6600);
    private static final Color COR_TEXTO   = new Color(0x1A1E2E);
    private static final Color COR_SUBTXT  = new Color(0x6B7280);
    private static final Color COR_VERDE   = new Color(0x22C55E);
    private static final Color COR_AMARELO = new Color(0xF59E0B);
    private static final Color COR_BORDA   = new Color(0xE8E8E8);

    // ── Dados do animal (podem vir do controller) ─────────────────────────
    private final String nomeAnimal;
    private final String nomeAdotante;
    private final String statusAnimal;

    // ── Componentes ──────────────────────────────────────────────────────────
    private JCheckBox checkTermo;
    private RoundedButton btnConfirmar;
    private JButton btnVoltar;

    public AdocaoView() {
        this("Preciosinha", "Lavanda Lara", "Disponível para adoção responsável");
    }

    public AdocaoView(String nomeAnimal, String nomeAdotante, String statusAnimal) {
        this.nomeAnimal   = nomeAnimal;
        this.nomeAdotante = nomeAdotante;
        this.statusAnimal = statusAnimal;

        setTitle("Lau Miau – Confirmar Adoção");
        setSize(820, 660);
        setMinimumSize(new Dimension(600, 580));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        criarTela();
    }

    private void criarTela() {
        JPanel fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(COR_FUNDO);
        setContentPane(fundo);

        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setMaximumSize(new Dimension(620, 9999));

        // ── Topo ─────────────────────────────────────────────────────────────
        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        topo.setMaximumSize(new Dimension(620, 60));

        btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(COR_TEXTO);
        btnVoltar.setBorder(new TutorView.RoundedBorder(12, COR_BORDA));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltar.setPreferredSize(new Dimension(44, 44));
        btnVoltar.addActionListener(e -> dispose());

        JLabel logo = new JLabel("LAU 🐾 MIAU", SwingConstants.CENTER);
        logo.setFont(new Font("SansSerif", Font.BOLD, 26));
        logo.setForeground(COR_LARANJA);

        topo.add(btnVoltar, BorderLayout.WEST);
        topo.add(logo, BorderLayout.CENTER);

        // ── Cabeçalho da tela ────────────────────────────────────────────────
        JLabel titulo = new JLabel("Confirmar Adoção");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        titulo.setForeground(COR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Confira os dados antes de finalizar");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitulo.setForeground(COR_SUBTXT);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Card central ─────────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new TutorView.RoundedBorder(20, COR_BORDA),
                new EmptyBorder(32, 40, 32, 40)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(620, 9999));

        // Linha de info – animal
        card.add(infoRow("🐱  Animal:", nomeAnimal, COR_LARANJA, 20, Font.BOLD));
        card.add(Box.createVerticalStrut(18));

        // Linha de info – adotante
        card.add(infoRow("👤  Adotante:", nomeAdotante, COR_TEXTO, 17, Font.PLAIN));
        card.add(Box.createVerticalStrut(18));

        // Badge de status
        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusRow.setOpaque(false);
        statusRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel badgeLabel = badgeLabel(statusAnimal, COR_VERDE);
        statusRow.add(badgeLabel);
        card.add(statusRow);
        card.add(Box.createVerticalStrut(24));

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(COR_BORDA);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        card.add(sep);
        card.add(Box.createVerticalStrut(22));

        // Texto do termo
        JTextArea texto = new JTextArea(
                "Ao confirmar esta adoção, você declara que o termo foi assinado "
                + "e que o adotante se responsabiliza pelos cuidados, segurança, saúde "
                + "e bem-estar do animal. Somente para casas fechadas e teladas."
        );
        texto.setWrapStyleWord(true);
        texto.setLineWrap(true);
        texto.setEditable(false);
        texto.setOpaque(false);
        texto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        texto.setForeground(COR_SUBTXT);
        texto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 9999));
        card.add(texto);
        card.add(Box.createVerticalStrut(22));

        // Aviso
        JPanel avisoPanel = new JPanel(new BorderLayout());
        avisoPanel.setBackground(new Color(0xFFF7ED));
        avisoPanel.setBorder(BorderFactory.createCompoundBorder(
                new TutorView.RoundedBorder(10, new Color(0xFED7AA)),
                new EmptyBorder(12, 16, 12, 16)
        ));
        avisoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JLabel aviso = new JLabel("⚠️  Verifique se o termo de adoção foi assinado antes de prosseguir.");
        aviso.setFont(new Font("SansSerif", Font.PLAIN, 13));
        aviso.setForeground(new Color(0x92400E));
        avisoPanel.add(aviso, BorderLayout.CENTER);
        card.add(avisoPanel);
        card.add(Box.createVerticalStrut(22));

        // Checkbox
        checkTermo = new JCheckBox("Li e confirmo que o termo de adoção foi assinado");
        checkTermo.setOpaque(false);
        checkTermo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        checkTermo.setForeground(COR_TEXTO);
        checkTermo.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(checkTermo);

        // ── Botão confirmar ──────────────────────────────────────────────────
        btnConfirmar = new RoundedButton("❤  Confirmar adoção");
        btnConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmar.addActionListener(e -> confirmarAdocao());

        // ── Rodapé ────────────────────────────────────────────────────────────
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        rodape.setOpaque(false);
        rodape.add(linkLabel("Política de Privacidade"));
        rodape.add(new JLabel("🐾"));
        rodape.add(linkLabel("Termos de Serviço"));
        rodape.setMaximumSize(new Dimension(620, 30));

        // ── Montagem ─────────────────────────────────────────────────────────
        wrapper.add(topo);
        wrapper.add(Box.createVerticalStrut(16));
        wrapper.add(titulo);
        wrapper.add(Box.createVerticalStrut(6));
        wrapper.add(subtitulo);
        wrapper.add(Box.createVerticalStrut(24));
        wrapper.add(card);
        wrapper.add(Box.createVerticalStrut(20));
        wrapper.add(btnConfirmar);
        wrapper.add(Box.createVerticalStrut(16));
        wrapper.add(rodape);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(30, 30, 30, 30);
        fundo.add(wrapper, gbc);
    }

    // ── Lógica ───────────────────────────────────────────────────────────────
    private void confirmarAdocao() {
        if (!checkTermo.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "⚠️  Você precisa confirmar que o termo foi assinado.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "Confirmar a adoção de \"" + nomeAnimal + "\" por " + nomeAdotante + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (resp == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "✅  Adoção de " + nomeAnimal + " realizada com sucesso!\n"
                    + "Que " + nomeAnimal + " tenha um lar cheio de amor! 🐾",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    // ── Helpers visuais ───────────────────────────────────────────────────────
    private JPanel infoRow(String label, String valor, Color corValor, int tamValor, int style) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setForeground(COR_SUBTXT);

        JLabel val = new JLabel(valor);
        val.setFont(new Font("SansSerif", style, tamValor));
        val.setForeground(corValor);

        row.add(lbl);
        row.add(val);
        return row;
    }

    private JLabel badgeLabel(String texto, Color cor) {
        JLabel badge = new JLabel("  ● " + texto + "  ") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("SansSerif", Font.BOLD, 13));
        badge.setForeground(cor);
        badge.setOpaque(false);
        badge.setBorder(new EmptyBorder(4, 10, 4, 10));
        return badge;
    }

    private JLabel linkLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.PLAIN, 12));
        l.setForeground(new Color(0x9AA0B0));
        return l;
    }

    // ── Entrypoint ────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdocaoView().setVisible(true));
    }
}