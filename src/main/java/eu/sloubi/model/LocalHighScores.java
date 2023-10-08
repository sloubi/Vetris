package eu.sloubi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import eu.sloubi.App;

public class LocalHighScores extends HighScores {

    @Override
    public void load() {
        try {
            scores = objectMapper.readValue(App.prefs.get("highScores", "[]"), new TypeReference<>() {
            });
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void save() {
        try {
            App.prefs.put("highScores", objectMapper.writeValueAsString(scores));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

}
