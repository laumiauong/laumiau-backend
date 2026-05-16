package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class AdocaoView extends JFrame {

    private static final Color COR_FUNDO = new Color(0xF5F5F5);
    private static final Color COR_CARD = Color.WHITE;
    private static final Color COR_LARANJA = new Color(0xFF6600);
    private static final Color COR_LARANJA_HOVER = new Color(0xE85D00);
    private static final Color COR_TEXTO = new Color(0x111827);
    private static final Color COR_SUBTEXTO = new Color(0x5E6475);
    private static final Color COR_BORDA = new Color(0xDDDDDD);
    private static final Color COR_VERDE = new Color(0x22C55E);
    private static final Color COR_VERDE_FUNDO = new Color(0xEAFBF1);

    private final String nomeAnimal;
    private final String nomeAdotante;

    private JCheckBox checkTermo;

    public AdocaoView() {
        this("Gatinho", "Lauanda");
    }

    public AdocaoView(String nomeAnimal, String nomeAdotante) {
        this.nomeAnimal = nomeAnimal;
        this.nomeAdotante = nomeAdotante;

        setTitle("Adoção - LauMiau");
        setSize(1000, 720);
        setMinimumSize(new Dimension(760, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        criarTela();
    }

    private void criarTela() {
        JPanel fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(COR_FUNDO);
        setContentPane(fundo);

        JPanel card = new JPanel();
        card.setBackground(COR_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(430, 610));
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(28, new Color(0xE8E8E8)),
                new EmptyBorder(26, 34, 24, 34)
        ));

        JLabel logo = criarLogo();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel slogan = new JLabel("Transformando vidas, espalhando amor.");
        slogan.setFont(new Font("SansSerif", Font.BOLD, 13));
        slogan.setForeground(COR_SUBTEXTO);
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Confirmar adoção");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        titulo.setForeground(COR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Confira os dados abaixo antes de finalizar.");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(COR_SUBTEXTO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(logo);
        card.add(Box.createVerticalStrut(8));
        card.add(slogan);
        card.add(Box.createVerticalStrut(34));
        card.add(titulo);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(24));

        card.add(labelCampo("Animal"));
        card.add(caixaInfoEmoji("🐱", nomeAnimal, COR_LARANJA, Color.WHITE));
        card.add(Box.createVerticalStrut(14));

        card.add(labelCampo("Adotante"));
        card.add(caixaInfoEmoji("👤", nomeAdotante, COR_LARANJA, Color.WHITE));
        card.add(Box.createVerticalStrut(14));

        card.add(labelCampo("Status"));
        card.add(caixaInfoEmoji("●", "Disponível para adoção responsável", COR_VERDE, COR_VERDE_FUNDO));
        card.add(Box.createVerticalStrut(20));

        JTextArea texto = new JTextArea(
                "Ao confirmar esta adoção, você declara que o termo foi assinado "
                + "e que o adotante se responsabiliza pelos cuidados, segurança, "
                + "saúde e bem-estar do animal."
        );
        texto.setEditable(false);
        texto.setFocusable(false);
        texto.setOpaque(false);
        texto.setBorder(null);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        texto.setForeground(COR_SUBTEXTO);
        texto.setMaximumSize(new Dimension(360, 72));
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(texto);
        card.add(Box.createVerticalStrut(18));

        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        checkPanel.setOpaque(false);
        checkPanel.setMaximumSize(new Dimension(360, 30));

        checkTermo = new JCheckBox("Li e confirmo que o termo foi assinado");
        checkTermo.setOpaque(false);
        checkTermo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        checkTermo.setForeground(COR_TEXTO);
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
        ou.setForeground(COR_SUBTEXTO);
        ou.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(ou);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setForeground(COR_LARANJA);
        btnVoltar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.addActionListener(e -> dispose());

        card.add(Box.createVerticalStrut(6));
        card.add(btnVoltar);

        fundo.add(card);
    }

    private JLabel criarLogo() {
        java.net.URL url = getClass().getResource("/img/logo-lau-miau.png");

        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(215, 70, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        }

        JLabel logoTexto = new JLabel("LAU 🐾 MIAU");
        logoTexto.setFont(new Font("SansSerif", Font.BOLD, 28));
        logoTexto.setForeground(COR_LARANJA);
        return logoTexto;
    }

    private JLabel labelCampo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(COR_TEXTO);
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
                new RoundedBorder(10, COR_BORDA),
                new EmptyBorder(0, 12, 0, 12)
        ));

        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblIcone.setForeground(corIcone);

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTexto.setForeground(corIcone == COR_VERDE ? COR_VERDE : COR_TEXTO);

        caixa.add(lblIcone, BorderLayout.WEST);
        caixa.add(lblTexto, BorderLayout.CENTER);

        return caixa;
    }

    private void confirmarAdocao() {
        if (!checkTermo.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Você precisa confirmar que o termo foi assinado.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Adoção de " + nomeAnimal + " confirmada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }

    static class RoundedButton extends JButton {

        public RoundedButton(String texto) {
            super(texto);
            setFont(new Font("SansSerif", Font.BOLD, 15));
            setForeground(Color.WHITE);
            setBackground(COR_LARANJA);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(COR_LARANJA_HOVER);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(COR_LARANJA);
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

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

    static class RoundedBorder extends AbstractBorder {

        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);

            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdocaoView().setVisible(true));
    }
}