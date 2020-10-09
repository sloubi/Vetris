package org.sloubi.model;

import java.awt.*;
import java.util.Random;

public class Shape implements Cloneable {
    protected Tetromino tetromino;
    protected int[][] squares;
    protected int x = 0;
    protected int y = 0;
    private final Random random = new Random();

    public Shape(Tetromino tetromino) {
        this.tetromino = tetromino;
        this.squares = tetromino.squares;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSquare(int x, int y) {
        return squares[y][x];
    }

    public int getWidth() {
        return squares[0].length;
    }

    public int getHeight() {
        return squares.length;
    }

    public Color getColor() {
        return tetromino.color;
    }

    public void rotate(boolean clockwise) {
        // Pas de rotation possible
        if (tetromino.possibleWays == 1)
            return;

        final int M = getHeight();
        final int N = getWidth();
        int[][] res = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (clockwise) {
                    res[c][M-1-r] = squares[r][c];
                }
                else {
                    res[N-1-c][r] = squares[r][c];
                }
            }
        }

        squares = res;
    }

    public void randomRotate() {
        int rotation = random.nextInt(tetromino.possibleWays);
        for (int i = 0; i < rotation; i++)
            rotate(true);
    }

    public void print() {
         for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                System.out.print(getSquare(x, y));
            }
            System.out.println();
        }
    }

}
