package eu.sloubi.view;

import eu.sloubi.App;
import eu.sloubi.model.Board;
import eu.sloubi.model.BoardListener;
import eu.sloubi.model.Score;

import javax.swing.*;
import java.awt.*;

public class Bottombar extends JPanel implements BoardListener {
    private final transient Board board;
    private final JLabel score = new JLabel();
    private final JLabel level = new JLabel();

    public Bottombar(Board board) {
        this.board = board;
        this.board.addListener(this);

        setBackground(Color.BLACK);

        Font font = App.barFont.deriveFont(24f);
        Color labelColor = new Color(228, 80, 0);
        Color valueColor = new Color(255, 255, 255);

        JLabel levelLabel = new JLabel("LEVEL");
        levelLabel.setFont(font);
        levelLabel.setForeground(labelColor);
        levelLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        add(levelLabel);

        level.setForeground(valueColor);
        level.setFont(font);
        level.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
        add(level);

        JLabel scoreLabel = new JLabel("SCORE");
        scoreLabel.setForeground(labelColor);
        scoreLabel.setFont(font);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        add(scoreLabel);

        score.setForeground(valueColor);
        score.setFont(font);
        add(score);

        // Initialisation des valeurs
        scoreChanged();
    }

    @Override
    public void boardChanged() {
        // Not needed
    }

    @Override
    public void holdChanged() {
        // Not needed
    }

    @Override
    public void scoreChanged() {
        level.setText(String.valueOf(board.getScore().getLevel()));
        score.setText(String.valueOf(board.getScore().getScore()));
    }

    @Override
    public void stateChanged() {
        // Not needed
    }

    @Override
    public void clockChanged() {
        // Not needed
    }

    @Override
    public void nextShapeChanged() {
        // Not needed
    }

    @Override
    public void newHighScore(Score score) {
        // Not needed
    }

    @Override
    public void userEvent(String event) {
        // Not needed
    }
}
