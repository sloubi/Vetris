package org.sloubi.model;

import java.awt.*;

public class Square {

    // Statuts des cases
    public enum State { Empty, Filled, Current }

    private State state;
    private Color color = Color.cyan;

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
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
