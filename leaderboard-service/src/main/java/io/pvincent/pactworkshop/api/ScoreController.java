package io.pvincent.pactworkshop.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pvincent.pactworkshop.score.GameScore;
import io.pvincent.pactworkshop.score.ScoresStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ScoreController {

    @Autowired
    private final ScoresStorageService scoresStorageService;

    public ScoreController(ScoresStorageService scoresStorageService) {
        this.scoresStorageService = scoresStorageService;
    }

    @PostMapping("/recordScore")
    @ResponseBody
    public ResponseEntity<RecordScoreResponseBody> recordScore(@RequestBody RecordScoreRequestBody request) {

        GameScore newScore = scoresStorageService.recordScore(request.getUsername(), request.getGame(), request.isWon());

        RecordScoreResponseBody response = new RecordScoreResponseBody(newScore.getPlayed(), newScore.getWon());
        return ResponseEntity.of(Optional.of(response));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RecordScoreRequestBody {

        private final String username;
        private final String game;
        private final boolean won;

        @JsonCreator
        public RecordScoreRequestBody(
                @JsonProperty("username") String username,
                @JsonProperty("game") String game,
                @JsonProperty("won") boolean won) {
            this.username = username;
            this.game = game;
            this.won = won;
        }

        @JsonProperty
        public String getUsername() {
            return username;
        }

        @JsonProperty
        public String getGame() {
            return game;
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
                @JsonProperty("gamesPlayed") int gamesWon) {
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

