package io.pvincent.pactworkshop.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pvincent.pactworkshop.score.GameScore;
import io.pvincent.pactworkshop.score.ScoresStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ScoreController {

    @Autowired
    private final ScoresStorageService scoresStorageService;

    public ScoreController(ScoresStorageService scoresStorageService) {
        this.scoresStorageService = scoresStorageService;
    }

    // WHAT IS THE ENDPOINT?
    @PostMapping("/users/{user}/games/{game}/score")
    @ResponseBody
    public ResponseEntity<RecordScoreResponseBody> recordScore(
            @PathVariable(name="user") String user,
            @PathVariable(name="game") String game,
            @RequestBody RecordScoreRequestBody request) {

        boolean won = request.isWon();

        // WHERE ARE THESE PARAMETERS COMING FROM?
        GameScore newScore = scoresStorageService.recordScore(user, game, won);

        // WHAT IS THE RESPONSE BODY?
        RecordScoreResponseBody response = new RecordScoreResponseBody(newScore.getPlayed(), newScore.getWon());
        return ResponseEntity.of(Optional.of(response));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RecordScoreRequestBody {

        private final boolean won;

        @JsonCreator
        public RecordScoreRequestBody(
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


        /*
        {
          “gamesPlayed”: 123,
          “gamesWon”: 55
        }
         */
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

