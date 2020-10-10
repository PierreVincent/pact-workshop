package io.pvincent.pactworkshop.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pvincent.pactworkshop.score.GameScore;
import io.pvincent.pactworkshop.score.ScoresStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
public class LeaderController {

    @Autowired
    private final ScoresStorageService scoresStorageService;

    public LeaderController(ScoresStorageService scoresStorageService) {
        this.scoresStorageService = scoresStorageService;
    }

    @GetMapping("/games/{game}/leader")
    @ResponseBody
    public ResponseEntity<LeaderController.LeaderResponseBody> getGameLeader(@PathVariable("game") String game) {
        String leader = scoresStorageService.getLeader(game);
        return ResponseEntity.of(Optional.of(new LeaderController.LeaderResponseBody(leader)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class LeaderResponseBody {

        private final String leader;

        @JsonCreator
        public LeaderResponseBody(@JsonProperty("leader") String leader) {
            this.leader = leader;
        }

        @JsonProperty
        public String getLeader() {
            return leader;
        }

    }

}
