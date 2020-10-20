package org.sloubi.model;

import org.sloubi.App;

import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    private static final long serialVersionUID = -4401162744040333360L;
    private int score;
    private int lines;
    private int time;
    private int level = 1;
    private boolean VShapeActive = true;
    private String version = App.version;
    private String name;

    public int getScore() {
        return score;
    }

    public int getLines() {
        return lines;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isVShapeActive() {
        return VShapeActive;
    }

    public void setName(String name) {
        name = name.trim();
        if (name.equals("")) name = "Anonymous";
        this.name = name;
    }

    public void setTime(int time) {
        this.time = time;
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

    @Override
    public String toString() {
        return name + " : " + score;
    }
}
