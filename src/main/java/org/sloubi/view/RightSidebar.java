package org.sloubi.view;

import org.sloubi.model.Board;
import org.sloubi.model.BoardListener;
import org.sloubi.model.Score;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RightSidebar extends JPanel implements BoardListener {

    private final Board board;
    private final JLabel score = new JLabel("0");
    private final JLabel lines = new JLabel("0");
    private final JLabel level = new JLabel("1");
    private final List<ShapePanel> shapePanels = new ArrayList<>();
    private JPanel mainPanel;
    private RoundedPanel nextPanel;
    private RoundedPanel sequencePanel;

    public RightSidebar(Board board) {
        this.board = board;
        this.board.addListener(this);

        initComponents();

        add(mainPanel);

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
    }

    private void initComponents() {
        initNextPanels();
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
        mainPanel.add(sequencePanel, gbc);
    }

    private void initNextPanels() {
        shapePanels.add(new ShapePanel(board.getNextShape(0), 10, 1));
        shapePanels.add(new ShapePanel(board.getNextShape(1), 10, 1));
        shapePanels.add(new ShapePanel(board.getNextShape(2), 10, 1));
        shapePanels.add(new ShapePanel(board.getNextShape(3), 10, 1));

        nextPanel = new RoundedPanel();
        nextPanel.setBorder(BorderFactory.createEmptyBorder(35, 10, 10, 10));
        nextPanel.setTitle("NEXT");
        nextPanel.add(shapePanels.get(0));

        sequencePanel = new RoundedPanel();
        sequencePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sequencePanel.setLayout(new GridLayout(3, 1, 0, 5));
        sequencePanel.add(shapePanels.get(1));
        sequencePanel.add(shapePanels.get(2));
        sequencePanel.add(shapePanels.get(3));
    }

    @Override
    public void boardChanged() {

    }

    @Override
    public void holdChanged() {

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
        shapePanels.get(0).setShape(board.getNextShape(0));
        shapePanels.get(1).setShape(board.getNextShape(1));
        shapePanels.get(2).setShape(board.getNextShape(2));
        shapePanels.get(3).setShape(board.getNextShape(3));
    }

    @Override
    public void newHighScore(Score score) {
    }

    @Override
    public void userEvent(String event) {

    }
}

