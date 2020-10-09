package org.sloubi.model;

public interface BoardListener {
    void boardChanged();
    void scoreChanged();
    void stateChanged();
    void clockChanged();
    void nextShapeChanged();
    void newHighScore(Score score);
}
