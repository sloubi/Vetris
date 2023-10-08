package eu.sloubi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sloubi.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class HighScores implements Iterable<Score> {

    private List<Score> scores = new ArrayList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public HighScores() {
        load();
    }

    public void add(Score s) {
        scores.add(s);
        scores.sort(Collections.reverseOrder());

        int kept = 10;
        if (scores.size() > kept)
            scores.remove(scores.size() - 1);
    }

    public int getMinScore() {
        if (scores.isEmpty()) return 0;
        return scores.get(scores.size() - 1).getScore();
    }

    public boolean isHighScore(Score s) {
        return s.getScore() > getMinScore();
    }

    public void load() {
        try {
            scores = objectMapper.readValue(App.prefs.get("highScores", "[]"), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public void save() {
        try {
            App.prefs.put("highScores", objectMapper.writeValueAsString(scores));
        } catch (JsonProcessingException e) {
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
