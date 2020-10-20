package org.sloubi.view;

import org.sloubi.App;
import org.sloubi.model.Board;
import org.sloubi.model.BoardListener;
import org.sloubi.model.Score;

import javax.swing.*;
import java.awt.*;

public class Bottombar extends JPanel implements BoardListener {
    private final Board board;
    private final JLabel score = new JLabel();
    private final JLabel level = new JLabel();
    private final Color labelColor = new Color(228, 80, 0);
    private final Color valueColor = new Color(255, 255, 255);

    public Bottombar(Board board) {
        this.board = board;
        this.board.addListener(this);

        setBackground(Color.BLACK);

        Font font = App.barFont.deriveFont(24f);

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

    }

    @Override
    public void holdChanged() {

    }

    @Override
    public void scoreChanged() {
        level.setText(String.valueOf(board.getScore().getLevel()));
        score.setText(String.valueOf(board.getScore().getScore()));
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
