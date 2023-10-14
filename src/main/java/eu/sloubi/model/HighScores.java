package eu.sloubi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class HighScores implements Iterable<Score> {

    protected List<Score> scores = new ArrayList<>();

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    protected HighScores() {
        load();
    }

    public void add(Score s) {
        scores.add(s);
        scores.sort(Collections.reverseOrder());

        int kept = 10;
        if (scores.size() > kept) {
            scores.remove(scores.size() - 1);
        }
    }

    public int getMinScore() {
        if (scores.isEmpty()) {
            return 0;
        }
        return scores.get(scores.size() - 1).getScore();
    }

    public boolean isHighScore(Score s) {
        return s.getScore() > getMinScore();
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

    abstract void load();

}
