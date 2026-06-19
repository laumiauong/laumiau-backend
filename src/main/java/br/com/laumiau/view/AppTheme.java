package br.com.laumiau.view;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class AppTheme {

    public static final Color LARANJA = new Color(255, 107, 43); // <-- Renomeado para LARANJA
    public static final Color LARANJA_HOVER = new Color(249, 115, 22);
    public static final Color FUNDO = new Color(253, 247, 242);
    public static final Color BRANCO = Color.WHITE;


    public static final Color TEXTO_DARK = new Color(31, 41, 55);
    public static final Color CINZA_TEXTO = new Color(107, 114, 128);
    public static final Color BORDA = new Color(230, 230, 230);


    public static final Font FONTE_TITULO = new Font("SansSerif", Font.BOLD, 28);
    public static final Font FONTE_LABEL = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONTE_TEXTO = new Font("SansSerif", Font.PLAIN, 14);


    public static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }
}