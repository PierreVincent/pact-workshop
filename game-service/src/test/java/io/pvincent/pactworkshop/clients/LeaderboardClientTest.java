package io.pvincent.pactworkshop.clients;

import io.pvincent.pactworkshop.score.GameScore;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.JsonBody;

import static org.junit.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class LeaderboardClientTest {

    private static ClientAndServer mockServer;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @AfterClass
    public static void stopServer() {
        mockServer.stop();
    }

    @Test
    public void testLeaderboardClientResponse() throws Exception {
        mockServer.when(
                request()
                    .withMethod("POST")
                    .withPath("/some/api/endpoint")
                    .withHeader("Content-Type", "application/json;charset=UTF-8")
                    .withBody(new JsonBody("{someString : 'some string', someNumber : 123}"))
        ).respond(
                response()
                    .withStatusCode(200)
                    .withBody(new JsonBody("{someString : 'some other string', someNumber : 42}"))
        );

        LeaderboardClient client = new LeaderboardClient("http://127.0.0.1:" + mockServer.getLocalPort());

        GameScore expectedResponse = new GameScore(4, 3);
        assertEquals(client.recordScore("john", "tic-tac-toe", true), expectedResponse);
    }


}
