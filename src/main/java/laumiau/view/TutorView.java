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
        setSize(900, 700);
        setMinimumSize(new Dimension(700, 620));
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
        wrapper.setMaximumSize(new Dimension(520, 9999));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);

        JButton btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(COR_TEXTO);
        btnVoltar.setBorder(new RoundedBorder(12, COR_BORDA));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltar.setPreferredSize(new Dimension(44, 44));
        btnVoltar.addActionListener(e -> dispose());

        JLabel logo = criarLogo();

        topo.add(btnVoltar, BorderLayout.WEST);
        topo.add(logo, BorderLayout.CENTER);
        topo.setMaximumSize(new Dimension(520, 60));

        JLabel titulo = new JLabel("Área do Tutor");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(COR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Acompanhe seus interesses e adoções");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(COR_PLACEHOLDER);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel card = new JPanel();
        card.setBackground(COR_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20, COR_BORDA),
                new EmptyBorder(32, 36, 32, 36)
        ));

        JLabel nome = new JLabel("Olá, Tutor!");
        nome.setFont(new Font("SansSerif", Font.BOLD, 22));
        nome.setForeground(COR_TEXTO);
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel email = new JLabel("email@exemplo.com");
        email.setFont(new Font("SansSerif", Font.PLAIN, 14));
        email.setForeground(COR_PLACEHOLDER);
        email.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton btnInteresse = new RoundedButton("🐾 Meus animais de interesse");
        RoundedButton btnAcompanhar = new RoundedButton("📋 Acompanhar adoção");
        RoundedButton btnEditar = new RoundedButton("✏ Editar perfil");
        RoundedButton btnSair = new RoundedButton("Sair");

        btnInteresse.addActionListener(e -> new AnimaisInteresseView().setVisible(true));
        btnAcompanhar.addActionListener(e -> new AcompanharAdocaoView().setVisible(true));
        btnEditar.addActionListener(e -> new EditarPerfilTutorView().setVisible(true));
        btnSair.addActionListener(e -> dispose());

        card.add(nome);
        card.add(Box.createVerticalStrut(6));
        card.add(email);
        card.add(Box.createVerticalStrut(30));
        card.add(btnInteresse);
        card.add(Box.createVerticalStrut(14));
        card.add(btnAcompanhar);
        card.add(Box.createVerticalStrut(14));
        card.add(btnEditar);
        card.add(Box.createVerticalStrut(14));
        card.add(btnSair);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        rodape.setOpaque(false);
        rodape.add(linkLabel("Política de Privacidade"));
        rodape.add(new JLabel("🐾"));
        rodape.add(linkLabel("Termos de Serviço"));

        wrapper.add(topo);
        wrapper.add(Box.createVerticalStrut(18));
        wrapper.add(titulo);
        wrapper.add(Box.createVerticalStrut(6));
        wrapper.add(subtitulo);
        wrapper.add(Box.createVerticalStrut(25));
        wrapper.add(card);
        wrapper.add(Box.createVerticalStrut(18));
        wrapper.add(rodape);

        fundo.add(wrapper);
    }

    private JLabel criarLogo() {
        JLabel logo = new JLabel("LAU 🐾 MIAU", SwingConstants.CENTER);
        logo.setFont(new Font("SansSerif", Font.BOLD, 26));
        logo.setForeground(COR_LARANJA);
        return logo;
    }

    private JLabel linkLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(COR_PLACEHOLDER);
        return label;
    }

    static class AnimaisInteresseView extends JFrame {
        public AnimaisInteresseView() {
            setTitle("Meus animais de interesse");
            setSize(650, 450);
            setLocationRelativeTo(null);

            JPanel fundo = basePanel();
            JLabel titulo = titulo("Meus animais de interesse");

            JTextArea lista = new JTextArea(
                    "🐱 Preciosinha - Disponível\n\n"
                    + "🐶 Rabinho - Em adoção\n\n"
                    + "🐱 Charmosa - Disponível"
            );
            lista.setEditable(false);
            lista.setFont(new Font("SansSerif", Font.PLAIN, 16));
            lista.setBorder(new EmptyBorder(25, 25, 25, 25));

            fundo.add(titulo, BorderLayout.NORTH);
            fundo.add(lista, BorderLayout.CENTER);
            add(fundo);
        }
    }

    static class AcompanharAdocaoView extends JFrame {
        public AcompanharAdocaoView() {
            setTitle("Acompanhar adoção");
            setSize(650, 450);
            setLocationRelativeTo(null);

            JPanel fundo = basePanel();
            JLabel titulo = titulo("Acompanhar adoção");

            JTextArea status = new JTextArea(
                    "Animal: Preciosinha\n"
                    + "Status: Em análise\n\n"
                    + "Sua solicitação foi recebida.\n"
                    + "A equipe Lau Miau irá avaliar as informações e entrar em contato."
            );
            status.setEditable(false);
            status.setFont(new Font("SansSerif", Font.PLAIN, 16));
            status.setBorder(new EmptyBorder(25, 25, 25, 25));

            JButton fechar = new RoundedButton("Entendi");
            fechar.addActionListener(e -> dispose());

            fundo.add(titulo, BorderLayout.NORTH);
            fundo.add(status, BorderLayout.CENTER);
            fundo.add(fechar, BorderLayout.SOUTH);
            add(fundo);
        }
    }

    static class EditarPerfilTutorView extends JFrame {

        private JTextField campoNome;
        private JTextField campoEmail;
        private JTextField campoTelefone;

        public EditarPerfilTutorView() {
            setTitle("Editar perfil");
            setSize(650, 500);
            setLocationRelativeTo(null);

            JPanel fundo = basePanel();
            JLabel titulo = titulo("Editar perfil");

            JPanel card = new JPanel();
            card.setBackground(Color.WHITE);
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(new EmptyBorder(30, 40, 30, 40));

            campoNome = campo("Nome do tutor");
            campoEmail = campo("email@exemplo.com");
            campoTelefone = campo("(45) 99999-9999");

            JButton salvar = new RoundedButton("Salvar alterações");
            salvar.addActionListener(e -> JOptionPane.showMessageDialog(
                    this,
                    "Perfil atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            ));

            card.add(campoNome);
            card.add(Box.createVerticalStrut(15));
            card.add(campoEmail);
            card.add(Box.createVerticalStrut(15));
            card.add(campoTelefone);
            card.add(Box.createVerticalStrut(25));
            card.add(salvar);

            fundo.add(titulo, BorderLayout.NORTH);
            fundo.add(card, BorderLayout.CENTER);
            add(fundo);
        }

        private JTextField campo(String texto) {
            JTextField campo = new JTextField(texto);
            campo.setFont(new Font("SansSerif", Font.PLAIN, 15));
            campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            campo.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(14, new Color(0xE8E8E8)),
                    new EmptyBorder(0, 18, 0, 18)
            ));
            return campo;
        }
    }

    private static JPanel basePanel() {
        JPanel fundo = new JPanel(new BorderLayout(20, 20));
        fundo.setBackground(COR_FUNDO);
        fundo.setBorder(new EmptyBorder(30, 40, 30, 40));
        return fundo;
    }

    private static JLabel titulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 26));
        label.setForeground(COR_TEXTO);
        return label;
    }

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
            setPreferredSize(new Dimension(360, 52));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setBackground(COR_LARANJA_HV);
                    repaint();
                }

                public void mouseExited(MouseEvent e) {
                    setBackground(COR_LARANJA);
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 26, 26));
            super.paintComponent(g);
            g2.dispose();
        }
    }

    static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TutorView().setVisible(true));
    }
}