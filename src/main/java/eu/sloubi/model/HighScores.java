package eu.sloubi.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class HighScores implements Serializable, Iterable<Score> {
    @Serial
    private static final long serialVersionUID = 8392154074028239629L;

    private final ArrayList<Score> scores = new ArrayList<>();
    private static final String HIGHSCORE_FILENAME = "highscore.ser";

    public void add(Score s) {
        scores.add(s);
        scores.sort(Collections.reverseOrder());

        int keeped = 10;
        if (scores.size() > keeped)
            scores.remove(scores.size() - 1);
    }

    public int getMinScore() {
        if (scores.isEmpty()) return 0;
        return scores.get(scores.size() - 1).getScore();
    }

    public boolean isHighScore(Score s) {
        return s.getScore() > getMinScore();
    }

    public static HighScores load() {
        HighScores highscores = new HighScores();

        File file = new File(HIGHSCORE_FILENAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                highscores = (HighScores) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }

        return highscores;
    }

    public void save() {
        File file = new File(HIGHSCORE_FILENAME);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Iterator<Score> iterator() {
        return scores.iterator();
    }

    public int size() {
        return scores.size();
    }

    public Score get(int index) {
        return scores.get(index);
    }
}
