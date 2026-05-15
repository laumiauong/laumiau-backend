package laumiau.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class TutorView extends JFrame {

    // ── Paleta Lau Miau ──────────────────────────────────────────────────────
    private static final Color COR_FUNDO      = new Color(0xFFF8F2);
    private static final Color COR_LARANJA    = new Color(0xFF6600);
    private static final Color COR_LARANJA_HV = new Color(0xE55C00);
    private static final Color COR_TEXTO      = new Color(0x1A1E2E);
    private static final Color COR_PLACEHOLDER= new Color(0x9AA0B0);
    private static final Color COR_BORDA      = new Color(0xE8E8E8);
    private static final Color COR_CARD       = Color.WHITE;

    // ── Campos ────────────────────────────────────────────────────────────────
    private RoundedField  campoNome;
    private RoundedField  campoEmail;
    private RoundedField  campoTelefone;
    private RoundedPassword campoSenha;
    private RoundedPassword campoConfSenha;
    private JCheckBox     checkTermos;
    private RoundedButton btnCadastrar;
    private JButton       btnVoltar;

    public TutorView() {
        setTitle("Lau Miau – Crie sua Conta");
        setSize(780, 700);
        setMinimumSize(new Dimension(600, 620));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        criarTela();
    }

    private void criarTela() {
        JPanel fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(COR_FUNDO);
        setContentPane(fundo);

        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setMaximumSize(new Dimension(500, 9999));

        // ── Topo ─────────────────────────────────────────────────────────────
        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);

        btnVoltar = new JButton("←");
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
        topo.setMaximumSize(new Dimension(500, 60));

        // ── Subtítulo ────────────────────────────────────────────────────────
        JLabel titulo = new JLabel("Crie sua Conta");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(COR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Card ─────────────────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setBackground(COR_CARD);
        card.setBorder(new RoundedBorder(20, COR_BORDA));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20, COR_BORDA),
                new EmptyBorder(32, 36, 32, 36)
        ));

        campoNome     = new RoundedField("👤  Nome Completo");
        campoEmail    = new RoundedField("✉  Email");
        campoTelefone = new RoundedField("📱  Telefone");
        campoSenha    = new RoundedPassword("🔒  Crie uma Senha");
        campoConfSenha= new RoundedPassword("🔒  Confirme sua Senha");

        checkTermos = new JCheckBox("<html>Li e concordo com os <font color='#FF6600'>Termos de Uso</font></html>");
        checkTermos.setOpaque(false);
        checkTermos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        checkTermos.setForeground(COR_TEXTO);
        checkTermos.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnCadastrar = new RoundedButton("Cadastrar");
        btnCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        btnCadastrar.addActionListener(e -> cadastrarTutor());

        card.add(campoNome);      card.add(Box.createVerticalStrut(14));
        card.add(campoEmail);     card.add(Box.createVerticalStrut(14));
        card.add(campoTelefone);  card.add(Box.createVerticalStrut(14));
        card.add(campoSenha);     card.add(Box.createVerticalStrut(14));
        card.add(campoConfSenha); card.add(Box.createVerticalStrut(18));
        card.add(checkTermos);    card.add(Box.createVerticalStrut(24));
        card.add(btnCadastrar);

        // ── Rodapé ────────────────────────────────────────────────────────────
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        rodape.setOpaque(false);
        JLabel pol = linkLabel("Política de Privacidade");
        JLabel pata = new JLabel("🐾");
        JLabel termos = linkLabel("Termos de Serviço");
        rodape.add(pol); rodape.add(pata); rodape.add(termos);
        rodape.setMaximumSize(new Dimension(500, 30));

        // ── Montagem ─────────────────────────────────────────────────────────
        wrapper.add(topo);
        wrapper.add(Box.createVerticalStrut(18));
        wrapper.add(titulo);
        wrapper.add(Box.createVerticalStrut(20));
        wrapper.add(card);
        wrapper.add(Box.createVerticalStrut(16));
        wrapper.add(rodape);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.insets = new Insets(30, 30, 30, 30);
        fundo.add(wrapper, gbc);
    }

    // ── Lógica de cadastro ────────────────────────────────────────────────────
    private void cadastrarTutor() {
        String nome      = campoNome.getValor("👤  Nome Completo");
        String email     = campoEmail.getValor("✉  Email");
        String telefone  = campoTelefone.getValor("📱  Telefone");
        String senha     = campoSenha.getValor("🔒  Crie uma Senha");
        String confSenha = campoConfSenha.getValor("🔒  Confirme sua Senha");

        // Campos obrigatórios
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()
                || senha.isEmpty() || confSenha.isEmpty()) {
            erro("Preencha todos os campos obrigatórios.");
            return;
        }

        // E-mail básico
        if (!email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
            erro("Informe um e-mail válido.");
            return;
        }

        // Senhas coincidem
        if (!senha.equals(confSenha)) {
            erro("As senhas não coincidem.");
            return;
        }

        // Força mínima da senha
        if (senha.length() < 6) {
            erro("A senha deve ter no mínimo 6 caracteres.");
            return;
        }

        // Termos
        if (!checkTermos.isSelected()) {
            erro("Você precisa aceitar os Termos de Uso.");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "✅  Conta criada com sucesso!\n\nBem-vindo(a), " + nome + "!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private void erro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private JLabel criarLogo() {
        JLabel logo = new JLabel("LAU 🐾 MIAU", SwingConstants.CENTER);
        logo.setFont(new Font("SansSerif", Font.BOLD, 26));
        logo.setForeground(COR_LARANJA);
        return logo;
    }

    private JLabel linkLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.PLAIN, 12));
        l.setForeground(COR_PLACEHOLDER);
        return l;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Componentes internos
    // ════════════════════════════════════════════════════════════════════════

    /** Campo de texto arredondado com placeholder */
    static class RoundedField extends JPanel {
        private final JTextField field;
        private final String placeholder;

        RoundedField(String placeholder) {
            this.placeholder = placeholder;
            setLayout(new BorderLayout());
            setBackground(new Color(0xF5F5F5));
            setBorder(new RoundedBorder(14, new Color(0xE8E8E8)));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
            setPreferredSize(new Dimension(360, 52));

            field = new JTextField(placeholder);
            field.setFont(new Font("SansSerif", Font.PLAIN, 14));
            field.setForeground(new Color(0x9AA0B0));
            field.setOpaque(false);
            field.setBorder(new EmptyBorder(0, 18, 0, 18));
            field.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (field.getText().equals(placeholder)) {
                        field.setText("");
                        field.setForeground(new Color(0x1A1E2E));
                    }
                }
                public void focusLost(FocusEvent e) {
                    if (field.getText().isEmpty()) {
                        field.setText(placeholder);
                        field.setForeground(new Color(0x9AA0B0));
                    }
                }
            });
            add(field, BorderLayout.CENTER);
        }

        String getValor(String ph) {
            String t = field.getText().trim();
            return t.equals(ph) ? "" : t;
        }
    }

    /** Campo de senha arredondado com placeholder e olho */
    static class RoundedPassword extends JPanel {
        private final JPasswordField field;
        private final String placeholder;
        private boolean visible = false;

        RoundedPassword(String placeholder) {
            this.placeholder = placeholder;
            setLayout(new BorderLayout());
            setBackground(new Color(0xF5F5F5));
            setBorder(new RoundedBorder(14, new Color(0xE8E8E8)));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
            setPreferredSize(new Dimension(360, 52));

            field = new JPasswordField(placeholder);
            field.setEchoChar((char) 0);
            field.setFont(new Font("SansSerif", Font.PLAIN, 14));
            field.setForeground(new Color(0x9AA0B0));
            field.setOpaque(false);
            field.setBorder(new EmptyBorder(0, 18, 0, 0));

            JButton eye = new JButton("👁");
            eye.setBorderPainted(false);
            eye.setFocusPainted(false);
            eye.setContentAreaFilled(false);
            eye.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            eye.setFont(new Font("SansSerif", Font.PLAIN, 14));
            eye.setBorder(new EmptyBorder(0, 4, 0, 12));
            eye.addActionListener(e -> {
                visible = !visible;
                if (visible) {
                    field.setEchoChar((char) 0);
                    eye.setText("🙈");
                } else {
                    if (!String.valueOf(field.getPassword()).equals(placeholder))
                        field.setEchoChar('●');
                    eye.setText("👁");
                }
            });

            field.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (String.valueOf(field.getPassword()).equals(placeholder)) {
                        field.setText("");
                        field.setForeground(new Color(0x1A1E2E));
                        if (!visible) field.setEchoChar('●');
                    }
                }
                public void focusLost(FocusEvent e) {
                    if (String.valueOf(field.getPassword()).isEmpty()) {
                        field.setText(placeholder);
                        field.setForeground(new Color(0x9AA0B0));
                        field.setEchoChar((char) 0);
                    }
                }
            });

            add(field, BorderLayout.CENTER);
            add(eye, BorderLayout.EAST);
        }

        String getValor(String ph) {
            String t = String.valueOf(field.getPassword()).trim();
            return t.equals(ph) ? "" : t;
        }
    }

    /** Botão laranja arredondado */
    static class RoundedButton extends JButton {
        RoundedButton(String text) {
            super(text);
            setFont(new Font("SansSerif", Font.BOLD, 16));
            setForeground(Color.WHITE);
            setBackground(new Color(0xFF6600));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(360, 52));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { setBackground(new Color(0xE55C00)); repaint(); }
                public void mouseExited(MouseEvent e)  { setBackground(new Color(0xFF6600)); repaint(); }
            });
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 26, 26));
            super.paintComponent(g);
            g2.dispose();
        }
    }

    /** Borda arredondada reutilizável */
    static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;
        RoundedBorder(int radius, Color color) { this.radius = radius; this.color = color; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) { return new Insets(radius/2, radius/2, radius/2, radius/2); }
    }

    // ── Entrypoint ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TutorView().setVisible(true));
    }
}