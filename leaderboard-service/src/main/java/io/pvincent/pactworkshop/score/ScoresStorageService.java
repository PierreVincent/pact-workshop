package io.pvincent.pactworkshop.score;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ScoresStorageService {

    private final static ConcurrentMap<String, ConcurrentMap<String, GameScore>> scoresDb = new ConcurrentHashMap<>();

    public GameScore recordScore(String username, String game, boolean won) {
        if (!scoresDb.containsKey(username)) {
            scoresDb.put(username, new ConcurrentHashMap<>());
        }
        GameScore score = scoresDb.get(username).getOrDefault(game, new GameScore(0,0));
        GameScore newScore = score.withNewResult(won);
        scoresDb.get(username).put(game, newScore);

        return newScore;
    }

}
