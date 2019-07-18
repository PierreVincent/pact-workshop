package io.pvincent.pactworkshop;

import io.pvincent.pactworkshop.clients.LeaderboardClient;
import io.pvincent.pactworkshop.games.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public GameService getGameService() {
        return new GameService();
    }

    @Bean
    public LeaderboardClient getLeaderboardClient() {
        return new LeaderboardClient("http://localhost:8080");
    }

}