package org.sloubi.view;

import org.sloubi.App;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    protected int borderSize = 6;
    protected Color borderColor = Color.white;
    protected boolean filled = false;
    protected Color titleColor = Color.black;
    protected float titleSize = 20f;
    protected int titleTopMargin = 0;
    protected Dimension arcs = new Dimension(20, 20);
    protected String title;

    public RoundedPanel() {
        super();

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderSize));

        if (filled) {
            g2.fillRoundRect(
                borderSize / 2, borderSize / 2,
                getWidth() - borderSize - 1, getHeight() - borderSize - 1,
                arcs.width, arcs.height);
        }
        else {
             g2.drawRoundRect(
                borderSize / 2, borderSize / 2,
                getWidth() - borderSize - 1, getHeight() - borderSize - 1,
                arcs.width, arcs.height);
        }

        if (title != null) {
            drawTitle(g2);
        }
    }

    private void drawTitle(Graphics2D g2) {
        Font font = App.barFont.deriveFont(titleSize);
        FontMetrics metrics = g2.getFontMetrics(font);

        g2.setColor(borderColor);
        g2.fillRect(borderSize, borderSize, getWidth() - borderSize * 2, metrics.getHeight());

        int x = (getWidth() - metrics.stringWidth(title)) / 2;
        int y = titleTopMargin != 0 ? titleTopMargin : borderSize * 2 + metrics.getHeight() / 2;

        g2.setColor(titleColor);
        g2.setFont(font);
        g2.drawString(title, x, y);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setTitleColor(Color titleColor) {
        this.titleColor = titleColor;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
    }

    public void setTitleTopMargin(int titleTopMargin) {
        this.titleTopMargin = titleTopMargin;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
