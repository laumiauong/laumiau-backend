package br.com.laumiau.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class SobreNosFrame extends JFrame {

    private static final Color LARANJA      = new Color(255, 107, 38);
    private static final Color LARANJA_DARK = new Color(230, 85, 20);
    private static final Color LARANJA_SOFT = new Color(255, 237, 227);
    private static final Color BRANCO       = Color.WHITE;
    private static final Color CINZA_BG     = new Color(245, 245, 247);
    private static final Color CINZA_BG2    = new Color(238, 238, 243);
    private static final Color CINZA_BORDA  = new Color(220, 220, 225);
    private static final Color TEXTO_DARK   = new Color(30, 30, 35);
    private static final Color TEXTO_MEDIO  = new Color(80, 80, 90);
    private static final Font  FONTE_UI     = new Font("Segoe UI", Font.PLAIN, 14);

    private JPanel headerPanel;
    private JPanel mainContent;
    private JPanel menuRight;
    private JLabel menuHome;
    private JLabel menuAnimais;
    private JPanel sobrePanel;
    private JPanel adminPanel;

    private static final int CARD_W = 980;
    private static final int CARD_H = 510;
    private static final int CARD_Y = 100;

    public SobreNosFrame() {
        initComponents();
        setSize(1200, 700);
        setLocationRelativeTo(null);

        getContentPane().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repositionAll();
            }
        });
    }

    private void repositionAll() {
        int w = getContentPane().getWidth();

        headerPanel.setBounds(0, 0, w, 72);

        int adminX   = w - 105 - 48;
        int sobreX   = adminX - 115 - 18;
        int animaisX = sobreX  -  70 - 22;
        int homeX    = animaisX -  55 - 18;

        menuHome.setBounds(homeX,    24,  55, 24);
        menuAnimais.setBounds(animaisX, 24,  70, 24);
        sobrePanel.setBounds(sobreX,  18, 115, 36);
        adminPanel.setBounds(adminX,  18, 105, 36);

        int cardX = (w - CARD_W) / 2;
        mainContent.setBounds(cardX, CARD_Y, CARD_W, CARD_H);

        headerPanel.repaint();
        mainContent.repaint();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lau Miau - Sobre Nós");
        setSize(1200, 700);

        JPanel backgroundPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, CINZA_BG,
                    getWidth(), getHeight(), CINZA_BG2
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 107, 38, 12));
                g2.fillOval(getWidth() - 300, -100, 400, 400);
                g2.setColor(new Color(255, 107, 38, 8));
                g2.fillOval(-100, getHeight() - 250, 350, 350);

                g2.dispose();
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        try {
            setIconImage(new ImageIcon(getClass().getResource("/imagens/logo.png")).getImage());
        } catch (Exception e) {
            System.out.println("Logo não encontrada");
        }

        // ── HEADER 
        headerPanel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, BRANCO, 0, getHeight(), new Color(252, 252, 253));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(230, 230, 235));
                g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                g2.dispose();
            }
        };
        headerPanel.setOpaque(false);

        // LAU 🐾 MIAU 
        JLabel lauLabel = new JLabel("LAU") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());
                // Sombra suave
                g2.setColor(new Color(255, 107, 38, 40));
                g2.drawString(getText(), 1, getHeight() - 6);
                g2.setColor(LARANJA);
                g2.drawString(getText(), 0, getHeight() - 7);
                g2.dispose();
            }
        };
        lauLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lauLabel.setForeground(LARANJA);
        lauLabel.setBounds(48, 22, 55, 28);
        headerPanel.add(lauLabel);

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/imagens/logo.png"));
            Image logoImg = logoIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
            JLabel logoImagem = new JLabel(new ImageIcon(logoImg));
            logoImagem.setBounds(106, 23, 26, 26);
            headerPanel.add(logoImagem);
        } catch (Exception e) {
            System.out.println("Logo não encontrada");
        }

        JLabel miauLabel = new JLabel("MIAU") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());
                g2.setColor(new Color(255, 107, 38, 40));
                g2.drawString(getText(), 1, getHeight() - 6);
                g2.setColor(LARANJA);
                g2.drawString(getText(), 0, getHeight() - 7);
                g2.dispose();
            }
        };
        miauLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        miauLabel.setForeground(LARANJA);
        miauLabel.setBounds(136, 22, 75, 28);
        headerPanel.add(miauLabel);

        menuHome = new JLabel("Home");
        menuHome.setFont(FONTE_UI);
        menuHome.setForeground(TEXTO_MEDIO);
        menuHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuHome.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { menuHome.setForeground(LARANJA); }
            @Override public void mouseExited(MouseEvent e)  { menuHome.setForeground(TEXTO_MEDIO); }
        });
        headerPanel.add(menuHome);

        menuAnimais = new JLabel("Animais");
        menuAnimais.setFont(FONTE_UI);
        menuAnimais.setForeground(TEXTO_MEDIO);
        menuAnimais.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuAnimais.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { menuAnimais.setForeground(LARANJA); }
            @Override public void mouseExited(MouseEvent e)  { menuAnimais.setForeground(TEXTO_MEDIO); }
        });
        headerPanel.add(menuAnimais);

        sobrePanel = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, LARANJA, 0, getHeight(), LARANJA_DARK);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                // Brilho superior sutil
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 10, 10);
                g2.dispose();
            }
        };
        sobrePanel.setOpaque(false);
        sobrePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel menuSobre = new JLabel("Sobre nós");
        menuSobre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuSobre.setForeground(BRANCO);
        sobrePanel.add(menuSobre);
        headerPanel.add(sobrePanel);

        adminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 8)) {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? new Color(252, 248, 245) : BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(hover ? LARANJA : CINZA_BORDA);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
        };
        adminPanel.setOpaque(false);
        adminPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel adminIcon = new JLabel("👤");
        JLabel adminText = new JLabel("Admin");
        adminText.setFont(FONTE_UI);
        adminText.setForeground(TEXTO_DARK);
        adminPanel.add(adminIcon);
        adminPanel.add(adminText);
        headerPanel.add(adminPanel);

        getContentPane().add(headerPanel);

        mainContent = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra suave em várias camadas
                for (int i = 12; i >= 1; i--) {
                    float alpha = (float)(1.8 * i);
                    g2.setColor(new Color(60, 40, 20, (int) alpha));
                    g2.fillRoundRect(i, i + 2, getWidth() - i * 2, getHeight() - i * 2, 22, 22);
                }

                // Fundo branco do card
                g2.setColor(BRANCO);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Linha de destaque laranja sutil no topo do card
                g2.setColor(new Color(255, 107, 38, 25));
                g2.fillRoundRect(0, 0, getWidth() - 1, 6, 20, 20);

                // Borda suave
                g2.setColor(new Color(230, 228, 225));
                g2.setStroke(new BasicStroke(0.8f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
            }
        };
        mainContent.setOpaque(false);

        // ── Imagem 
        int imgW = 310, imgH = 420;
        int imgX = CARD_W - imgW - 50;
        int imgY = (CARD_H - imgH) / 2;

        int moldOffX = imgX - 12;
        int moldOffY = imgY - 12;
        JPanel imagemPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int offX = imgX - moldOffX;
                int offY = imgY - moldOffY;
                GradientPaint moldGp = new GradientPaint(offX, offY, LARANJA_SOFT,
                        offX + imgW + 12, offY + imgH + 12, new Color(255, 215, 190));
                g2.setPaint(moldGp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);

                // Imagem recortada com cantos arredondados
                int ix = offX, iy = offY;
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/vet.png"));
                    Image imgOriginal = icon.getImage();
                    double ratW  = (double) imgW / imgOriginal.getWidth(this);
                    double ratH  = (double) imgH / imgOriginal.getHeight(this);
                    double scale = Math.max(ratW, ratH);
                    int fW = (int)(imgOriginal.getWidth(this)  * scale);
                    int fH = (int)(imgOriginal.getHeight(this) * scale);
                    int dx = (imgW - fW) / 2;
                    int dy = (imgH - fH) / 2;
                    g2.setClip(new RoundRectangle2D.Double(ix, iy, imgW, imgH, 18, 18));
                    g2.drawImage(imgOriginal, ix + dx, iy + dy, fW, fH, this);
                    // Gradiente sutil na parte inferior da imagem
                    GradientPaint fade = new GradientPaint(
                        0, iy + imgH - 80, new Color(0, 0, 0, 0),
                        0, iy + imgH,      new Color(0, 0, 0, 35)
                    );
                    g2.setPaint(fade);
                    g2.fillRect(ix, iy + imgH - 80, imgW, 80);
                } catch (Exception e) {
                    g2.setColor(new Color(235, 235, 235));
                    g2.fillRoundRect(ix, iy, imgW, imgH, 18, 18);
                }
                g2.dispose();
            }
        };
        imagemPanel.setBounds(moldOffX, moldOffY, imgW + 24, imgH + 24);
        imagemPanel.setOpaque(false);
        mainContent.add(imagemPanel);

        // ── Texto 
        int txtX      = 55;
        int textAreaW = imgX - txtX - 60;

        JPanel acento = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, LARANJA, getWidth(), 0, LARANJA_DARK);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.dispose();
            }
        };
        acento.setBounds(txtX, 48, 56, 5);
        acento.setOpaque(false);
        mainContent.add(acento);

        JLabel titulo = new JLabel("Sobre nós!");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titulo.setForeground(TEXTO_DARK);
        titulo.setBounds(txtX, 62, 320, 50);
        mainContent.add(titulo);

        String textoHtml = "<html><body style='width:" + textAreaW + "px;"
            + "line-height:1.7;color:#505060;font-size:13px;'>"
            + "<p>O Lau Miau nasceu com a missão de aproximar animais incríveis "
            + "de lares cheios de amor, agora, por meio de uma plataforma "
            + "moderna e interativa!</p><br>"
            + "<p>Aqui, quem deseja adotar encontra um guia fácil e divertido de "
            + "localizar cães e gatos que estão prontos para dar e receber carinho.</p><br>"
            + "<p>À frente do projeto está a <b>Dra. Lavanda Lara</b>, médica "
            + "veterinária e pet sitter, dedicada ao bem-estar animal. Com "
            + "ampla experiência e amor pela causa, ela também oferece "
            + "serviços profissionais.</p><br>"
            + "<p>Ideais para quem busca confiança e carinho nos momentos em que "
            + "não pode estar presente.</p><br>"
            + "<p>Para saber mais sobre os serviços veterinários ou de pet sitter, "
            + "basta entrar em contato. O cuidado com os animais é levado a sério!</p>"
            + "</body></html>";

        int textMaxH = (CARD_H - 76) - 122 - 14;

        JLabel textoLabel = new JLabel(textoHtml);
        textoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textoLabel.setVerticalAlignment(SwingConstants.TOP);
        textoLabel.setBounds(txtX, 122, textAreaW, textMaxH);
        mainContent.add(textoLabel);

        // ── Botões com sombra ─────────────────────────────────────────────────
        int btnY = CARD_H - 76;

        JButton btnConhecer = new JButton("Conhecer serviços") {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Sombra do botão
                g2.setColor(new Color(255, 107, 38, hover ? 60 : 40));
                g2.fillRoundRect(2, 4, getWidth() - 2, getHeight() - 2, 10, 10);
                // Gradiente do botão
                GradientPaint gp = new GradientPaint(0, 0, hover ? LARANJA_DARK : LARANJA,
                                                     0, getHeight(), hover ? new Color(200, 65, 10) : LARANJA_DARK);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                // Brilho interno superior
                g2.setColor(new Color(255, 255, 255, hover ? 15 : 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btnConhecer.setBounds(txtX, btnY, 190, 42);
        btnConhecer.setForeground(BRANCO);
        btnConhecer.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnConhecer.setFocusPainted(false);
        btnConhecer.setBorderPainted(false);
        btnConhecer.setContentAreaFilled(false);
        btnConhecer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainContent.add(btnConhecer);

        JButton btnContato = new JButton("Entre em contato") {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Sombra suave
                g2.setColor(new Color(200, 180, 170, hover ? 35 : 20));
                g2.fillRoundRect(2, 4, getWidth() - 2, getHeight() - 2, 10, 10);
                // Fundo
                g2.setColor(hover ? new Color(255, 245, 240) : BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                // Borda laranja com gradiente
                GradientPaint bp = new GradientPaint(0, 0, LARANJA, getWidth(), getHeight(), LARANJA_DARK);
                g2.setPaint(bp);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btnContato.setBounds(txtX + 206, btnY, 190, 42);
        btnContato.setForeground(LARANJA);
        btnContato.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnContato.setFocusPainted(false);
        btnContato.setBorderPainted(false);
        btnContato.setContentAreaFilled(false);
        btnContato.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnContato.addActionListener(e -> {
            try {
                new FormularioContatoFrame().setVisible(true);
            } catch (Exception ex) {
                System.out.println("FormularioContatoFrame não encontrado");
            }
        });
        mainContent.add(btnContato);

        getContentPane().add(mainContent);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new SobreNosFrame().setVisible(true));
    }
}
