package org.sloubi.view;

import org.sloubi.model.Board;
import org.sloubi.model.BoardListener;
import org.sloubi.model.Score;

import javax.swing.*;
import java.awt.*;

public class RightSidebar extends JPanel implements BoardListener {

    private final Board board;
    private final JLabel score = new JLabel("0");
    private final JLabel lines = new JLabel("0");
    private final JLabel level = new JLabel("1");
    private ShapePanel nextShapePanel;
    private JPanel mainPanel;
    private RoundedPanel nextPanel;

    public RightSidebar(Board board) {
        this.board = board;
        this.board.addListener(this);

        initComponents();

        add(mainPanel);

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
    }

    private void initComponents() {
        initNextPanel();
        initMainPanel();
    }

    private void initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 26, 0);

        mainPanel.add(nextPanel, gbc);
    }

    private void initNextPanel() {
        nextShapePanel = new ShapePanel(board.getNextShape(), 10, 1);

        nextPanel = new RoundedPanel();
        nextPanel.setBorder(BorderFactory.createEmptyBorder(35, 10, 10, 10));
        nextPanel.setTitle("NEXT");
        nextPanel.add(nextShapePanel);
    }

    @Override
    public void boardChanged() {

    }

    @Override
    public void scoreChanged() {
        Score s = board.getScore();
        level.setText(String.valueOf(s.getLevel()));
        score.setText(String.valueOf(s.getScore()));
        lines.setText(String.valueOf(s.getLines()));
    }

    @Override
    public void stateChanged() {
    }

    @Override
    public void clockChanged() {

    }

    @Override
    public void nextShapeChanged() {
        nextShapePanel.setShape(board.getNextShape());
    }

    @Override
    public void newHighScore(Score score) {
    }
}

