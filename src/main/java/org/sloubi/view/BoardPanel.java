package org.sloubi.view;

import org.sloubi.model.Board;
import org.sloubi.model.BoardListener;
import org.sloubi.model.Score;
import org.sloubi.model.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoardPanel extends JPanelExtented implements KeyListener, BoardListener {

    private final Board board;
    private final int SquareSize = 20;
    private final int SquareBorder = 2;
    private final Font gameFont;

    public BoardPanel(Board board, Font gameFont) {
        this.board = board;
        this.gameFont = gameFont;

        setFocusable(true);
        addKeyListener(this);
        this.board.addListener(this);

        setPreferredSize(new Dimension(board.getWidth() * SquareSize + SquareBorder * board.getWidth(),
                board.getHeight() * SquareSize + SquareBorder * board.getHeight()));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle rect;
        GradientPaint gp;

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Square square = board.getSquare(x, y);
                if (square.getState() == Square.State.Empty) {
                    g2.setPaint(Color.black);
                }
                else {
                    g2.setPaint(square.getColor());
                }

                rect = new Rectangle(x * SquareSize + SquareBorder * x, y * SquareSize + SquareBorder * y, SquareSize, SquareSize);
                g2.fill(rect);

                // On dessine un petit dégradé pour donner du relief au pièce
                if (square.getState() != Square.State.Empty) {
                    gp = new GradientPaint(
                            x * SquareSize + SquareBorder * x,
                            y * SquareSize + SquareBorder * y,
                            square.getColor().darker(),
                            x * SquareSize + SquareBorder * x + SquareSize,
                            y * SquareSize + SquareBorder * y + SquareSize,
                            new Color(255, 255, 255, 0));
                    g2.setPaint(gp);
                    g2.fill(rect);
                }
            }
        }

        if (board.getState() != Board.GameState.InGame) {
            drawCenteredString(g, board.getState().toString().toUpperCase(), gameFont);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            board.tryToMove(0, 1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            board.tryToMove(1, 0);
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            board.tryToMove(-1, 0);
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            board.rotateShape(true);
        }
        else if (e.getKeyChar() == 'z') {
            board.rotateShape(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_PAUSE || e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (board.getState() == Board.GameState.Over)
                board.init();
            else
                board.pause();
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            board.hardDrop();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void boardChanged() {
        repaint();
    }

    @Override
    public void scoreChanged() {

    }

    @Override
    public void stateChanged() {
        repaint();
    }

    @Override
    public void clockChanged() {

    }

    @Override
    public void nextShapeChanged() {

    }

    @Override
    public void newHighScore(Score score) {
        String response = JOptionPane.showInputDialog(this,
                board.getScore().getScore() + " est un nouveau record ! Quel est ton nom ?",
                "Nouveau record !",
                JOptionPane.INFORMATION_MESSAGE);
        if (response != null) {
            score.setName(response);
            board.getHighscores().add(score);
            board.getHighscores().save();
        }
    }
}
