package org.sloubi.view;

import org.sloubi.App;
import org.sloubi.model.Board;
import org.sloubi.model.BoardListener;
import org.sloubi.model.Score;

import javax.swing.*;
import java.awt.*;

public class LeftSidebar extends JPanel implements BoardListener {
    private final Board board;
    private final JLabel lines = new JLabel();
    private final ShapePanel holdShapePanel;

    public LeftSidebar(Board board) {
        this.board = board;
        this.board.addListener(this);

        holdShapePanel = new ShapePanel(board.getHoldedShape(), 10, 1);

        RoundedPanel holdPanel = new RoundedPanel();
        holdPanel.setBorder(BorderFactory.createEmptyBorder(35, 10, 10, 10));
        holdPanel.setTitle("HOLD");
        holdPanel.add(holdShapePanel);

        RoundedPanel linesPanel = new RoundedPanel();
        linesPanel.setTitle("LINES");
        linesPanel.setFilled(true);
        linesPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 4, 30));
        linesPanel.add(lines);
        lines.setFont(App.barFont.deriveFont(24f));

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 26, 0);

        container.add(holdPanel, gbc);
        container.add(linesPanel, gbc);

        add(container);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        scoreChanged();
    }

    @Override
    public void boardChanged() {

    }

    @Override
    public void holdChanged() {
        holdShapePanel.setShape(board.getHoldedShape());
    }

    @Override
    public void scoreChanged() {
        lines.setText(String.valueOf(board.getScore().getLines()));
    }

    @Override
    public void stateChanged() {

    }

    @Override
    public void clockChanged() {

    }

    @Override
    public void nextShapeChanged() {

    }

    @Override
    public void newHighScore(Score score) {

    }

    @Override
    public void tetris() {

    }
}
