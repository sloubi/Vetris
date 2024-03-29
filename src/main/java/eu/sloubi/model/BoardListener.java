package eu.sloubi.model;

public interface BoardListener {
    void boardChanged();

    void holdChanged();

    void scoreChanged();

    void stateChanged();

    void clockChanged();

    void nextShapeChanged();

    void newHighScore(Score score);

    void userEvent(String event);
}
