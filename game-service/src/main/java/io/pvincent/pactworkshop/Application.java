package io.pvincent.pactworkshop;

import io.pvincent.pactworkshop.clients.LeaderboardClient;
import io.pvincent.pactworkshop.games.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private Environment env;

    @Bean
    public GameService getGameService() {
        return new GameService();
    }

    @Bean
    public LeaderboardClient getLeaderboardClient() {
        return new LeaderboardClient(env.getProperty("leaderboardService.baseUrl"));
    }

}