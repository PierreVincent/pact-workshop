package io.pvincent.pactworkshop.clients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
        RecordScoreRequestBody request = new RecordScoreRequestBody();

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
        // WHAT PAYLOAD TO SEND?
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RecordScoreResponseBody {
        // WHAT PAYLOAD TO EXPECT BACK?
    }

}
