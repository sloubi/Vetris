package org.sloubi.view;

import org.sloubi.model.Board;
import org.sloubi.model.Square;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel implements SquareDrawer {

    private final Board board;
    private final int squareSize = 20;
    private final int squareBorder = 2;

    public BoardPanel(Board board) {
        this.board = board;

        setPreferredSize(new Dimension(board.getWidth() * squareSize + squareBorder * board.getWidth(),
                board.getHeight() * squareSize + squareBorder * board.getHeight()));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Square square = board.getSquare(x, y);

                drawSquare(g2,
                        square.getColor(),
                        squareSize,
                        squareBorder,
                        0,
                        0,
                        x,
                        y,
                        square.hasGradient());
            }
        }
    }
}
