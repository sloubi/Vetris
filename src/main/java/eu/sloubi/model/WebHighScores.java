package eu.sloubi.model;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebHighScores extends HighScores {

    public static final String HIGHSCORES_URI = "https://sloubi.eu/vetris-highscores";

    @Override
    public void load() {
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(HIGHSCORES_URI)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            scores = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void saveScore(final Score score) {
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(HIGHSCORES_URI))
                    .header("Accept", "*/*")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(score)))
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
