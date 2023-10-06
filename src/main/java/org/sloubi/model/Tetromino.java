package org.sloubi.model;

import java.awt.*;

public enum Tetromino {
    S_SHAPE(new int[][]{
            {1, 0},
            {1, 1},
            {0, 1}
    }, 2, new Color(66, 182, 66)),
    Z_SHAPE(new int[][]{
            {1, 1, 0},
            {0, 1, 1},
    }, 2, new Color(239, 32, 41)),
    T_SHAPE(new int[][]{
            {0, 1, 0},
            {1, 1, 1}
    }, 4, new Color(173, 77, 156)),
    L_SHAPE(new int[][]{
            {1, 0},
            {1, 0},
            {1, 1}
    }, 4, new Color(239, 121, 33)),
    MIRRORED_L_SHAPE(new int[][]{
            {0, 1},
            {0, 1},
            {1, 1}
    }, 4, new Color(90, 101, 173)),
    LINE_SHAPE(new int[][]{
            {1, 1, 1, 1},
    }, 2, new Color(49, 199, 239)),
    SQUARE_SHAPE(new int[][]{
            {1, 1},
            {1, 1},
    }, 1, new Color(247, 211, 8)),
    V_SHAPE(new int[][]{
            {1, 0, 1},
            {0, 1, 0},
    }, 1, new Color(181, 208, 207));

    final int[][] squares;
    final int possibleWays;
    final Color color;

    Tetromino(int[][] squares, int possibleWays, Color color) {
        this.squares = squares;
        this.possibleWays = possibleWays;
        this.color = color;
    }
}
