package org.sloubi.model;

import java.awt.*;

public enum Tetromino {
    SShape(new int[][] {
            {1, 0},
            {1, 1},
            {0, 1}
        }, 2, new Color(249, 65, 68)),
    ZShape(new int[][] {
            {1, 1, 0},
            {0, 1, 1},
        }, 2, new Color(243, 114, 44)),
    TShape(new int[][] {
            {0, 1, 0},
            {1, 1, 1}
        }, 4, new Color(248, 150, 30)),
    LShape(new int[][] {
            {1, 0},
            {1, 0},
            {1, 1}
        }, 4, new Color(249, 132, 74)),
    MirroredLShape(new int[][] {
            {0, 1},
            {0, 1},
            {1, 1}
        }, 4, new Color(249, 199, 79)),
    LineShape(new int[][] {
            {1, 1, 1, 1},
        }, 2, new Color(144, 190, 109)),
    SquareShape(new int[][] {
            {1, 1},
            {1, 1},
        }, 1, new Color(67, 170, 139)),
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
