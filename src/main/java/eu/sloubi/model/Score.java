package eu.sloubi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;

public class Score implements Comparable<Score> {

    private int score;
    private int lines;
    private int seconds;
    private int level = 1;
    private boolean vShapeActive = true;
    private String name;
    private int droppedPieces = 0;
    private LocalDateTime dateTime;

    @JsonIgnore
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Score() {
        objectMapper.registerModule(new JavaTimeModule());
    }

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

    public int getDroppedPieces() {
        return droppedPieces;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * @return Lines per minute
     */
    @JsonIgnore
    public int getLPM() {
        return seconds != 0 ? Math.round((float) lines / (float) seconds * 60) : 0;
    }

    /**
     * @return Tetriminos per minute
     */
    @JsonIgnore
    public int getTPM() {
        return seconds != 0 ? Math.round((float) droppedPieces / (float) seconds * 60) : 0;
    }

    public boolean isvShapeActive() {
        return vShapeActive;
    }

    public void setName(String name) {
        name = name.trim();
        if (name.isEmpty()) {
            name = "Anonymous";
        }
        this.name = name;
    }

    public void setDateTime() {
        this.dateTime = LocalDateTime.now();
    }

    public void addSecond() {
        seconds++;
    }

    public void setvShapeActive(boolean vShapeActive) {
        this.vShapeActive = vShapeActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

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
        switch (nbOfLines) {
            case 1 -> score += 100 * level;
            case 2 -> score += 300 * level;
            case 3 -> score += 500 * level;
            case 4 -> score += 800 * level; // Tetris !
            default -> throw new IllegalStateException("Wtf just happened");
        }

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

    public void addDroppedPiece() {
        droppedPieces++;
    }

    @Override
    public String toString() {
        return name + " : " + score;
    }

    public void saveOnWeb() {
        WebHighScores.saveScore(this);
    }

    public String getRelativeDateTime() {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(dateTime);
    }
}
