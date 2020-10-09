package org.sloubi.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class HighScores implements Serializable, Iterable<Score> {
    private static final long serialVersionUID = 8392154074028239629L;

    private final ArrayList<Score> scores = new ArrayList<>();
    private static final String highScoreFilename = "highscore.ser";

    public void add(Score s) {
        scores.add(s);
        scores.sort(Collections.reverseOrder());
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

        File file = new File(highScoreFilename);
        if (file.exists()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (ois != null) {
                    highscores = (HighScores) ois.readObject();
                    ois.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return highscores;
    }

    public void save() {
        File file = new File(highScoreFilename);

        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        for (Score s : scores) {
            System.out.println(s.getName() + " " + s.getScore());
        }
    }


    @Override
    public Iterator<Score> iterator() {
        return scores.iterator();
    }
}
