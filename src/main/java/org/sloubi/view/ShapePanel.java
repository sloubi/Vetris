package org.sloubi.view;

import javax.swing.*;
import org.sloubi.model.Shape;

import java.awt.*;

public class ShapePanel extends JPanel {
    private Shape shape;
    private final int squareSize;

    public ShapePanel(Shape shape, int squareSize) {
        this.shape = shape;
        this.squareSize = squareSize;

        setOpaque(false);

        // La taille du shapePanel ne change jamais
        // Elle correspond à la taille de la pièce la plus grande dans les 2 sens
        setPreferredSize(new Dimension(4 * squareSize + 8, 4 * squareSize + 8));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dimension de la pièce
        int shapeWidth = shape.getWidth() * squareSize + 2 * shape.getWidth();
        int shapeHeight = shape.getHeight() * squareSize + 2 * shape.getHeight();

        // Pour centrer la pièce
        int startX = (getWidth() - shapeWidth) / 2;
        int startY = (getHeight() - shapeHeight) / 2;

        Graphics2D g2 = (Graphics2D) g;
        Rectangle rect;
        GradientPaint gp;

        for (int y = 0; y < shape.getHeight(); y++) {
            for (int x = 0; x < shape.getWidth(); x++) {
                if (shape.getSquare(x, y) == 1) {
                    g2.setPaint(shape.getColor());
                    rect = new Rectangle(startX + x * squareSize + 2 * x,
                            startY + y * squareSize + 2 * y, squareSize, squareSize);
                    g2.fill(rect);

                    // On dessine un petit dégradé pour donner du relief au pièce
                    gp = new GradientPaint(
                            startX + x * squareSize + 2 * x,
                            startY + y * squareSize + 2 * y,
                            shape.getColor().darker(),
                            startX + x * squareSize + 2 * x + squareSize,
                            startY + y * squareSize + 2 * y + squareSize,
                            new Color(255, 255, 255, 0));
                    g2.setPaint(gp);
                    g2.fill(rect);
                }
            }
        }
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        repaint();
    }
}
