package org.sloubi.model;

import java.awt.*;

public class Square {

    // Statuts des cases
    public enum State { Empty, Filled, Current, End }

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
        if (state == State.Empty)
            c = new Color(33, 33, 33);
        else if (state == State.End) {
            c = new Color(50, 50, 50);
        }
        return c;
    }

    public boolean hasGradient() {
        return state == State.Filled || state == State.Current;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
