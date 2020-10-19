package org.sloubi.view;

import org.sloubi.App;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

class TitlePanel extends JPanel {

    public TitlePanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(100, 70));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCenteredString(g, "Vetris");
    }

    public void drawCenteredString(Graphics g, String text) {
        Graphics2D g2d = (Graphics2D) g;

        Font font = App.gameFont.deriveFont(36f);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = metrics.getAscent() + 6;

        g2d.translate(x, y);

        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc, text);

        // Bordure
        g2d.setColor(Color.white);
        BasicStroke wideStroke = new BasicStroke(5);
        g2d.setStroke(wideStroke);
        g2d.draw(gv.getOutline());

        // Texte
        g2d.setColor(new Color(228, 80, 0));
        g2d.fill(gv.getOutline());
    }
}
