package org.sloubi.view;

import javax.swing.*;
import java.awt.*;

public class BackgroundContainer extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(new Color(13, 15, 22));
        g2.fillRect(0, 0, getWidth(), getHeight());

        Color color1 = new Color(12, 12, 12);
        Color color2 = new Color(13, 15, 22);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, 200, color2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), 200);

        Color c1 = new Color(13, 15, 22, 240).brighter();
        Color c2 = new Color(13, 15, 22, 190).brighter();
        Color c3 = new Color(13, 15, 22, 140).brighter();

        g2.setColor(c1);
        g2.fillRect(20, 30, 20, 20);
        g2.setColor(c2);
        g2.fillRect(40, 66, 35, 35);
        g2.setColor(c3);
        g2.fillRect(34, 120, 18, 18);

        g2.setColor(c1);
        g2.fillRect(140, 400, 45, 45);
        g2.setColor(c3);
        g2.fillRect(114, 435, 30, 30);
        g2.setColor(c2);
        g2.fillRect(134, 476, 20, 20);

        g2.setColor(c1);
        g2.fillRect(getWidth() - 80, 500, 20, 20);
        g2.setColor(c3);
        g2.fillRect(getWidth() - 70, 540, 30, 30);
        g2.setColor(c2);
        g2.fillRect(getWidth() - 95, 538, 15, 15);
        g2.setColor(c3);
        g2.fillRect(getWidth() - 120, 560, 11, 11);
    }
}