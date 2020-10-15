package io.pvincent.pactworkshop.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pvincent.pactworkshop.score.GameScore;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LeaderboardClient {

    private RestTemplate rest;

    private final String baseUrl;

    public LeaderboardClient(String url) {
        this.baseUrl = url;
        this.rest = new RestTemplate();
    }

    public GameScore recordScore(String username, String game, boolean won) throws LeaderboardClientException {

        // WHAT ENDPOINT TO CALL?
        String recordScoreUrl = baseUrl + "/users/" +username+"/games/"+game+"/score";

        // WHAT TO SET IN PAYLOAD?
        RecordScoreRequestBody request = new RecordScoreRequestBody(won);

        HttpEntity<RecordScoreRequestBody> requestEntity = new HttpEntity<>(request);
        ResponseEntity<RecordScoreResponseBody> responseEntity =
                rest.exchange(recordScoreUrl, HttpMethod.POST, requestEntity, RecordScoreResponseBody.class);

        if (responseEntity.getStatusCode().isError()) {
            throw new LeaderboardClientException("Something went wrong when trying to record score");
        } else {
            RecordScoreResponseBody response = responseEntity.getBody();

            // HOW TO FILL IN THE SCORE FROM RESPONSE?
            return new GameScore(response.getGamesPlayed(), response.getGamesWon());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RecordScoreRequestBody {

        private final boolean won;

        @JsonCreator
        RecordScoreRequestBody(
                @JsonProperty("won") boolean won) {
            this.won = won;
        }

        @JsonProperty
        public boolean isWon() {
            return won;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RecordScoreResponseBody {

        private final int gamesPlayed;
        private final int gamesWon;

        @JsonCreator
        public RecordScoreResponseBody(
                @JsonProperty("gamesPlayed") int gamesPlayed,
                @JsonProperty("gamesWon") int gamesWon) {
            this.gamesPlayed = gamesPlayed;
            this.gamesWon = gamesWon;
        }

        @JsonProperty
        public int getGamesPlayed() {
            return gamesPlayed;
        }

        @JsonProperty
        public int getGamesWon() {
            return gamesWon;
        }

    }

}
