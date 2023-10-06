package org.sloubi.model;

import java.io.Serial;
import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    @Serial
    private static final long serialVersionUID = -4401162744040333360L;
    private int score;
    private int lines;
    private int seconds;
    private int level = 1;
    private boolean vShapeActive = true;
    private String name;
    private int piecesDropped = 0;

    public int getScore() {
        return score;
    }

    public int getLines() {
        return lines;
    }

    public int getSeconds() {
        return seconds;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    /**
     * @return Lines per minute
     */
    public int getLPM() {
        return seconds != 0 ? Math.round((float) lines / (float) seconds * 60) : 0;
    }

    /**
     * @return Tetriminos per minute
     */
    public int getTPM() {
        return seconds != 0 ? Math.round((float) piecesDropped / (float) seconds * 60) : 0;
    }

    public boolean isvShapeActive() {
        return vShapeActive;
    }

    public void setName(String name) {
        name = name.trim();
        if (name.isEmpty()) name = "Anonymous";
        this.name = name;
    }

    public void addSecond() {
        seconds++;
    }

    public void setvShapeActive(boolean vShapeActive) {
        this.vShapeActive = vShapeActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;

        return score == score1.score;
    }

    @Override
    public int hashCode() {
        return score;
    }

    @Override
    public int compareTo(Score score) {
        return Integer.compare(this.getScore(), score.getScore());
    }

    public void addLines(int nbOfLines) {
        if (nbOfLines == 1) score += 100 * level;
        else if (nbOfLines == 2) score += 300 * level;
        else if (nbOfLines == 3) score += 500 * level;
        else if (nbOfLines == 4) score += 800 * level; // Tetris !

        lines += nbOfLines;
    }

    /**
     * Met à jour le niveau
     *
     * @return True si le niveau a changé
     */
    public boolean updateLevel() {
        int linesForNextLevel = level * 10;
        if (lines >= linesForNextLevel) {
            level++;
            return true;
        }
        return false;
    }

    public void addPieceDropped() {
        piecesDropped++;
    }

    @Override
    public String toString() {
        return name + " : " + score;
    }
}
