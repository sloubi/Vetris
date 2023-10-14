package eu.sloubi.view;

import eu.sloubi.App;
import eu.sloubi.model.Shape;

import javax.swing.*;
import java.awt.*;

public class ShapePanel extends JPanel implements SquareDrawer {
    private transient Shape shape;
    private final int squareSize;
    private final int borderSize;

    public ShapePanel(eu.sloubi.model.Shape shape, int squareSize, int borderSize) {
        this.shape = shape;
        this.squareSize = squareSize;
        this.borderSize = borderSize;

        setOpaque(false);

        // La taille du shapePanel ne change jamais
        // Elle correspond à la taille de la pièce la plus grande dans les 2 sens
        setPreferredSize(new Dimension(4 * squareSize + 4 * borderSize, 4 * squareSize + 4 * borderSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (shape == null) {
            return;
        }

        // Dimension de la pièce
        int shapeWidth = shape.getWidth() * squareSize + borderSize * shape.getWidth();
        int shapeHeight = shape.getHeight() * squareSize + borderSize * shape.getHeight();

        // Pour centrer la pièce
        int offsetX = (getWidth() - shapeWidth) / 2;
        int offsetY = (getHeight() - shapeHeight) / 2;

        Graphics2D g2 = (Graphics2D) g;

        for (int y = 0; y < shape.getHeight(); y++) {
            for (int x = 0; x < shape.getWidth(); x++) {
                if (shape.getSquare(x, y) == 1) {
                    drawSquare(g2, shape.getColor(), squareSize, borderSize, offsetX, offsetY, x, y, App.prefs.getBoolean("reliefOnThePieces", true));
                }
            }
        }
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        repaint();
    }
}
