package eu.sloubi.model;

import eu.sloubi.App;

import java.awt.*;

public class Square {

    // Statuts des cases
    public enum State {EMPTY, FILLED, CURRENT, END}

    private State state;
    private Color color;

    public Square(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Color getColor() {
        Color c = color;
        if (state == State.EMPTY)
            c = new Color(33, 33, 33);
        else if (state == State.END) {
            c = new Color(50, 50, 50);
        }
        return c;
    }

    public boolean hasGradient() {
        return App.prefs.getBoolean("reliefOnThePieces", true) &&
                (state == State.FILLED || state == State.CURRENT);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
