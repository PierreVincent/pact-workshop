package io.pvincent.pactworkshop.clients;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerificationResult;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.RequestResponsePact;
import io.pvincent.pactworkshop.score.GameScore;
import org.junit.Rule;
import org.junit.Test;

import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest;
import static org.junit.Assert.assertEquals;

public class LeaderboardClientPactTest {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("test_provider", "localhost", 8080, this);

    @Test
    public void pactTestExample() {
        RequestResponsePact pact = ConsumerPactBuilder
                .consumer("game-service")
                .hasPactWith("leaderboard-service")
                .uponReceiving("a request to record a score")
                .path("/users/john/games/tic-tac-toe/score")
                .method("POST")
                .body("{\"won\": true}")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("gamesWon", 3)
                        .integerType("gamesPlayed", 4)
                )
                .toPact();

        MockProviderConfig config = MockProviderConfig.createDefault();
        PactVerificationResult result = runConsumerTest(pact, config, (mockServer, context) -> {
            LeaderboardClient client = new LeaderboardClient(mockServer.getUrl());

            GameScore expectedResponse = new GameScore(4, 3);
            assertEquals(expectedResponse, client.recordScore("john", "tic-tac-toe", true));
        });

        if (result instanceof PactVerificationResult.Error) {
            throw new RuntimeException(((PactVerificationResult.Error) result).getError());
        }

        assertEquals(PactVerificationResult.Ok.INSTANCE, result);
    }

}
