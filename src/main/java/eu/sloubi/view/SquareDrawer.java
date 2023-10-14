package eu.sloubi.view;

import java.awt.*;

public interface SquareDrawer {
    default void drawSquare(Graphics2D g2, Color squareColor, int squareSize, int borderSize, int offsetX, int offsetY, int x, int y,
            boolean gradient) {
        final int x1 = offsetX + x * squareSize + borderSize * x;
        final int y1 = offsetY + y * squareSize + borderSize * y;

        Rectangle rect = new Rectangle(x1, y1, squareSize, squareSize);

        g2.setPaint(squareColor);
        g2.fill(rect);

        if (gradient) {
            // On dessine un petit dégradé pour donner du relief au pièce
            GradientPaint gp = new GradientPaint(
                    x1,
                    y1,
                    squareColor.darker(),
                    (float) offsetX + x * squareSize + borderSize * x + squareSize,
                    (float) offsetY + y * squareSize + borderSize * y + squareSize,
                    new Color(255, 255, 255, 0));
            g2.setPaint(gp);
            g2.fill(rect);
        }
    }
}
