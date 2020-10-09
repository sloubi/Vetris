package org.sloubi.view;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class JPanelExtented extends JPanel {

    public void drawCenteredString(Graphics g, String text, Font font) {
        Graphics2D g2d = (Graphics2D) g;

        font = font.deriveFont(36f);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.translate(x, y);

        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc, text);

        // Bordure
        g2d.setColor(Color.white);
        BasicStroke wideStroke = new BasicStroke(5);
        g2d.setStroke(wideStroke);
        g2d.draw(gv.getOutline());

        // Texte
        g2d.setColor(new Color(69, 162, 158));
        g2d.fill(gv.getOutline());
    }

}
