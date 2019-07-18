package io.pvincent.pactworkshop.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pvincent.pactworkshop.clients.LeaderboardClient;
import io.pvincent.pactworkshop.clients.LeaderboardClientException;
import io.pvincent.pactworkshop.games.GameResult;
import io.pvincent.pactworkshop.games.GameService;
import io.pvincent.pactworkshop.games.UnkownGameException;
import io.pvincent.pactworkshop.score.GameScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    private final GameService gameService;

    @Autowired
    private final LeaderboardClient leaderboardClient;

    public GameController(GameService gameService, LeaderboardClient leaderboardClient) {
        this.gameService = gameService;
        this.leaderboardClient = leaderboardClient;
    }

    @PostMapping("/play")
    @ResponseBody
    public ResponseEntity<PlayResponseBody> play(@RequestBody PlayRequestBody request) {
        try {
            GameResult result = gameService.playGame(request.getGame(), request.getChoice());
            GameScore gameScore = leaderboardClient.recordScore(request.getUsername(), request.getGame(), result.isWon());

            return ResponseEntity.of(Optional.of(new PlayResponseBody(result, gameScore)));
        } catch (UnkownGameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (LeaderboardClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PlayRequestBody {

        private final String game;
        private final String username;
        private final String choice;

        @JsonCreator
        public PlayRequestBody(
                @JsonProperty("game") String game,
                @JsonProperty("username") String username,
                @JsonProperty(value = "choice") String choice) {
            this.game = game;
            this.username = username;
            this.choice = choice;
        }

        @JsonProperty
        String getGame() {
            return game;
        }

        @JsonProperty
        String getUsername() {
            return username;
        }

        @JsonProperty
        String getChoice() {
            return choice;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PlayResponseBody {

        private final boolean won;
        private final String message;
        private final int totalPlays;
        private final int totalWins;
        private final int winRate;


        PlayResponseBody(GameResult result, GameScore score) {
            this.won = result.isWon();
            this.message = result.getMessage();
            this.totalPlays = score.getPlayed();
            this.totalWins = score.getWon();
            this.winRate = 100*score.getWon()/score.getPlayed();
        }

        @JsonProperty
        public boolean isWon() {
            return won;
        }

        @JsonProperty
        public String getMessage() {
            return message;
        }

        @JsonProperty
        public int getTotalPlays() {
            return totalPlays;
        }

        @JsonProperty
        public int getTotalWins() {
            return totalWins;
        }

        @JsonProperty
        public int getWinRate() {
            return winRate;
        }
    }

}

