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
        String recordScoreUrl = baseUrl + "/some/api/endpoint";

        // WHAT TO SET IN PAYLOAD?
        RecordScoreRequestBody request = new RecordScoreRequestBody("some string", 123);

        HttpEntity<RecordScoreRequestBody> requestEntity = new HttpEntity<>(request);
        ResponseEntity<RecordScoreResponseBody> responseEntity =
                rest.exchange(recordScoreUrl, HttpMethod.POST, requestEntity, RecordScoreResponseBody.class);

        if (responseEntity.getStatusCode().isError()) {
            throw new LeaderboardClientException("Something went wrong when trying to record score");
        } else {
            RecordScoreResponseBody response = responseEntity.getBody();

            // HOW TO FILL IN THE SCORE FROM RESPONSE?
            return new GameScore(4, 3);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RecordScoreRequestBody {

        private final String someString;
        private final int someNumber;

        @JsonCreator
        RecordScoreRequestBody(
                @JsonProperty("someString") String someString,
                @JsonProperty("someNumber") int someNumber) {
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
                @JsonProperty("someString") String someString,
                @JsonProperty("someNumber") int someNumber) {
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
