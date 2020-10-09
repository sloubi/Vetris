package org.sloubi.view;

import java.awt.*;

class TitlePanel extends JPanelExtented {
    private Font font;

    public TitlePanel(Font font) {
        this.font = font;

        setOpaque(false);
        setPreferredSize(new Dimension(100, 44));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCenteredString(g, "Vetris", font.deriveFont(30f));
    }
}
