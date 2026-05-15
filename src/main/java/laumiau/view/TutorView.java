package laumiau.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class TutorView extends JFrame {

    private static final Color COR_FUNDO = new Color(0xFFF8F2);
    private static final Color COR_LARANJA = new Color(0xFF6600);
    private static final Color COR_LARANJA_HV = new Color(0xE55C00);
    private static final Color COR_TEXTO = new Color(0x1A1E2E);
    private static final Color COR_PLACEHOLDER = new Color(0x9AA0B0);
    private static final Color COR_BORDA = new Color(0xE8E8E8);
    private static final Color COR_CARD = Color.WHITE;

    public TutorView() {
        setTitle("Lau Miau – Área do Tutor");
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
        card.setPreferredSize(new Dimension(540, 650));

        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(50, new Color(0xE8E8E8)),
                new EmptyBorder(40, 50, 40, 50)
        ));

        JLabel logo = criarLogo();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel slogan = new JLabel("Transformando vidas, espalhando amor.");
        slogan.setFont(new Font("SansSerif", Font.BOLD, 14));
        slogan.setForeground(COR_PLACEHOLDER);
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Área do Tutor");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 38));
        titulo.setForeground(COR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Acompanhe seus interesses e adoções");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitulo.setForeground(COR_PLACEHOLDER);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nome = new JLabel("Olá, Tutor!");
        nome.setFont(new Font("SansSerif", Font.BOLD, 24));
        nome.setForeground(COR_TEXTO);
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel email = new JLabel("email@exemplo.com");
        email.setFont(new Font("SansSerif", Font.PLAIN, 14));
        email.setForeground(COR_PLACEHOLDER);
        email.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton btnInteresse = new RoundedButton("🐾  Meus animais de interesse");
        RoundedButton btnAcompanhar = new RoundedButton("📋  Acompanhar adoção");
        RoundedButton btnEditar = new RoundedButton("✏  Editar perfil");
        RoundedButton btnSair = new RoundedButton("Sair");

        configurarBotao(btnInteresse);
        configurarBotao(btnAcompanhar);
        configurarBotao(btnEditar);
        configurarBotao(btnSair);

        btnInteresse.addActionListener(e -> new AnimaisInteresseView().setVisible(true));
        btnAcompanhar.addActionListener(e -> new AcompanharAdocaoView().setVisible(true));
        btnEditar.addActionListener(e -> new EditarPerfilTutorView().setVisible(true));
        btnSair.addActionListener(e -> dispose());

        card.add(logo);
        card.add(Box.createVerticalStrut(10));
        card.add(slogan);
        card.add(Box.createVerticalStrut(36));
        card.add(titulo);
        card.add(Box.createVerticalStrut(10));
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(36));
        card.add(nome);
        card.add(Box.createVerticalStrut(6));
        card.add(email);
        card.add(Box.createVerticalStrut(36));
        card.add(btnInteresse);
        card.add(Box.createVerticalStrut(16));
        card.add(btnAcompanhar);
        card.add(Box.createVerticalStrut(16));
        card.add(btnEditar);
        card.add(Box.createVerticalStrut(16));
        card.add(btnSair);
        card.add(Box.createVerticalStrut(28));

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        rodape.setOpaque(false);

        rodape.add(linkLabel("Política de Privacidade"));
        rodape.add(new JLabel("🐾"));
        rodape.add(linkLabel("Termos de Serviço"));

        card.add(rodape);

        fundo.add(card);
    }

    private void configurarBotao(RoundedButton botao) {
        botao.setPreferredSize(new Dimension(390, 54));
        botao.setMaximumSize(new Dimension(390, 54));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JLabel criarLogo() {

        java.net.URL url = getClass().getResource("/img/logo-lau-miau.png");

        if (url != null) {

            ImageIcon icon = new ImageIcon(url);

            Image img = icon.getImage().getScaledInstance(
                    240,
                    85,
                    Image.SCALE_SMOOTH
            );

            return new JLabel(new ImageIcon(img));
        }

        JLabel logo = new JLabel("LAU 🐾 MIAU");
        logo.setFont(new Font("SansSerif", Font.BOLD, 34));
        logo.setForeground(COR_LARANJA);

        return logo;
    }

    private JLabel linkLabel(String texto) {

        JLabel label = new JLabel(texto);

        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(COR_PLACEHOLDER);

        return label;
    }

    // =========================================================
    // TELA MEUS ANIMAIS
    // =========================================================

    static class AnimaisInteresseView extends JFrame {

        public AnimaisInteresseView() {

            setTitle("Meus animais de interesse");
            setSize(650, 450);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel fundo = basePanel();
            JPanel card = cardInterno();

            JLabel titulo = titulo("Meus animais de interesse");

            JTextArea lista = new JTextArea(
                    "🐱 Gatinho - Disponível\n\n"
                    + "🐶 Rabinho - Em adoção\n\n"
                    + "🐱 Charmosa - Disponível"
            );

            lista.setEditable(false);
            lista.setFocusable(false);
            lista.setOpaque(false);
            lista.setFont(new Font("SansSerif", Font.PLAIN, 16));
            lista.setForeground(COR_TEXTO);

            card.add(titulo);
            card.add(Box.createVerticalStrut(25));
            card.add(lista);

            fundo.add(card);
            add(fundo);
        }
    }

    // =========================================================
    // TELA ACOMPANHAR ADOÇÃO
    // =========================================================

    static class AcompanharAdocaoView extends JFrame {

        public AcompanharAdocaoView() {

            setTitle("Acompanhar adoção");
            setSize(650, 450);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel fundo = basePanel();
            JPanel card = cardInterno();

            JLabel titulo = titulo("Acompanhar adoção");

            JTextArea status = new JTextArea(
                    "Animal: Gatinho\n"
                    + "Status: Em análise\n\n"
                    + "Sua solicitação foi recebida.\n"
                    + "A equipe Lau Miau irá avaliar as informações e entrar em contato."
            );

            status.setEditable(false);
            status.setFocusable(false);
            status.setOpaque(false);
            status.setFont(new Font("SansSerif", Font.PLAIN, 16));
            status.setForeground(COR_TEXTO);
            status.setLineWrap(true);
            status.setWrapStyleWord(true);

            RoundedButton fechar = new RoundedButton("Entendi");

            fechar.setPreferredSize(new Dimension(320, 48));
            fechar.setMaximumSize(new Dimension(320, 48));
            fechar.setAlignmentX(Component.CENTER_ALIGNMENT);

            fechar.addActionListener(e -> dispose());

            card.add(titulo);
            card.add(Box.createVerticalStrut(25));
            card.add(status);
            card.add(Box.createVerticalStrut(25));
            card.add(fechar);

            fundo.add(card);
            add(fundo);
        }
    }

    // =========================================================
    // EDITAR PERFIL
    // =========================================================

    static class EditarPerfilTutorView extends JFrame {

        private JTextField campoNome;
        private JTextField campoEmail;
        private JTextField campoTelefone;

        public EditarPerfilTutorView() {

            setTitle("Editar perfil");
            setSize(650, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel fundo = basePanel();
            JPanel card = cardInterno();

            JLabel titulo = titulo("Editar perfil");

            campoNome = campo("Nome do tutor");
            campoEmail = campo("email@exemplo.com");
            campoTelefone = campo("(45) 99999-9999");

            RoundedButton salvar = new RoundedButton("Salvar alterações");

            salvar.setPreferredSize(new Dimension(320, 48));
            salvar.setMaximumSize(new Dimension(320, 48));
            salvar.setAlignmentX(Component.CENTER_ALIGNMENT);

            salvar.addActionListener(e ->
                    JOptionPane.showMessageDialog(
                            this,
                            "Perfil atualizado com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE
                    )
            );

            card.add(titulo);
            card.add(Box.createVerticalStrut(28));
            card.add(campoNome);
            card.add(Box.createVerticalStrut(15));
            card.add(campoEmail);
            card.add(Box.createVerticalStrut(15));
            card.add(campoTelefone);
            card.add(Box.createVerticalStrut(28));
            card.add(salvar);

            fundo.add(card);
            add(fundo);
        }

        private JTextField campo(String texto) {

            JTextField campo = new JTextField(texto);

            campo.setFont(new Font("SansSerif", Font.PLAIN, 15));

            campo.setMaximumSize(new Dimension(320, 42));
            campo.setPreferredSize(new Dimension(320, 42));

            campo.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(18, COR_BORDA),
                    new EmptyBorder(0, 18, 0, 18)
            ));

            return campo;
        }
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private static JPanel basePanel() {

        JPanel fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(COR_FUNDO);

        return fundo;
    }

    private static JPanel cardInterno() {

        JPanel card = new JPanel();

        card.setBackground(Color.WHITE);

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setPreferredSize(new Dimension(430, 340));

        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(40, COR_BORDA),
                new EmptyBorder(34, 40, 34, 40)
        ));

        return card;
    }

    private static JLabel titulo(String texto) {

        JLabel label = new JLabel(texto);

        label.setFont(new Font("SansSerif", Font.BOLD, 26));
        label.setForeground(COR_TEXTO);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    // =========================================================
    // BOTÃO CUSTOMIZADO
    // =========================================================

    static class RoundedButton extends JButton {

        RoundedButton(String text) {

            super(text);

            setFont(new Font("SansSerif", Font.BOLD, 16));

            setForeground(Color.WHITE);
            setBackground(COR_LARANJA);

            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);

            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(COR_LARANJA_HV);
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

            g2.fill(new RoundRectangle2D.Float(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    24,
                    24
            ));

            FontMetrics fm = g2.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(getText())) / 2;

            int y = ((getHeight() - fm.getHeight()) / 2)
                    + fm.getAscent();

            g2.setColor(getForeground());
            g2.setFont(getFont());

            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }

    // =========================================================
    // BORDA CUSTOMIZADA
    // =========================================================

    static class RoundedBorder extends AbstractBorder {

        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(
                Component c,
                Graphics g,
                int x,
                int y,
                int w,
                int h
        ) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.drawRoundRect(
                    x,
                    y,
                    w - 1,
                    h - 1,
                    radius,
                    radius
            );

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {

            return new Insets(
                    radius / 2,
                    radius / 2,
                    radius / 2,
                    radius / 2
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new TutorView().setVisible(true)
        );
    }
}