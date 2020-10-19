package org.sloubi.model;

import java.awt.*;

public enum Tetromino {
    SShape(new int[][] {
            {1, 0},
            {1, 1},
            {0, 1}
        }, 2, new Color(66, 182, 66)),
    ZShape(new int[][] {
            {1, 1, 0},
            {0, 1, 1},
        }, 2, new Color(239, 32, 41)),
    TShape(new int[][] {
            {0, 1, 0},
            {1, 1, 1}
        }, 4, new Color(173, 77, 156)),
    LShape(new int[][] {
            {1, 0},
            {1, 0},
            {1, 1}
        }, 4, new Color(239, 121, 33)),
    MirroredLShape(new int[][] {
            {0, 1},
            {0, 1},
            {1, 1}
        }, 4, new Color(90, 101, 173)),
    LineShape(new int[][] {
            {1, 1, 1, 1},
        }, 2, new Color(49, 199, 239)),
    SquareShape(new int[][] {
            {1, 1},
            {1, 1},
        }, 1, new Color(247, 211, 8)),
    VShape(new int[][] {
            {1, 0, 1},
            {0, 1, 0},
    }, 1, new Color(181, 208, 207));

    protected final int[][] squares;
    protected final int possibleWays;
    protected final Color color;

    Tetromino(int[][] squares, int possibleWays, Color color) {
        this.squares = squares;
        this.possibleWays = possibleWays;
        this.color = color;
    }
}
