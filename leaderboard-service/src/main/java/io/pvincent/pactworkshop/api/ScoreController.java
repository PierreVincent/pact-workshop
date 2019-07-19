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

    // WHAT IS THE ENDPOINT?
    @PostMapping("/some/api/endpoint")
    @ResponseBody
    public ResponseEntity<RecordScoreResponseBody> recordScore(@RequestBody RecordScoreRequestBody request) {

        // WHERE ARE THESE PARAMETERS COMING FROM?
        GameScore newScore = scoresStorageService.recordScore("some_username", "some_game", true);

        // WHAT IS THE RESPONSE BODY?
        RecordScoreResponseBody response = new RecordScoreResponseBody("some_string", 123);
        return ResponseEntity.of(Optional.of(response));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RecordScoreRequestBody {

        private final String someString;
        private final int someNumber;

        @JsonCreator
        public RecordScoreRequestBody(
                @JsonProperty("some_string") String someString,
                @JsonProperty("some_number") int someNumber) {
            this.someString = someString;
            this.someNumber = someNumber;
        }

        @JsonProperty
        public String getSomeString() {
            return someString;
        }

        @JsonProperty
        public int getSomeNumber() {
            return someNumber;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RecordScoreResponseBody {

        private final String someString;
        private final int someNumber;

        @JsonCreator
        public RecordScoreResponseBody(
                @JsonProperty("some_string") String someString,
                @JsonProperty("some_number") int someNumber) {
            this.someString = someString;
            this.someNumber = someNumber;
        }

        @JsonProperty
        public String getSomeString() {
            return someString;
        }

        @JsonProperty
        public int getSomeNumber() {
            return someNumber;
        }
    }

}

