package eu.sloubi.view;

import eu.sloubi.App;
import eu.sloubi.model.Board;
import eu.sloubi.model.Square;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel implements SquareDrawer {

    private final transient Board board;
    private int squareSize;
    private int squareBorder;

    public BoardPanel(Board board) {
        this.board = board;

        updateSize();
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

                if (square != null) {
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

    public void updateSize() {
        squareSize = App.prefs.getInt("squareSize", 20);
        squareBorder = App.prefs.getInt("gridSize", 2);

        setPreferredSize(new Dimension(board.getWidth() * squareSize + squareBorder * (board.getWidth() - 1),
                board.getHeight() * squareSize + squareBorder * (board.getHeight() - 1)));
    }
}
