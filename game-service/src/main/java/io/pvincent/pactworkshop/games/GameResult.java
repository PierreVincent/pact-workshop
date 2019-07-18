package io.pvincent.pactworkshop.games;

public class GameResult {

    private final boolean won;
    private final String message;

    public GameResult(boolean won, String message) {
        this.won = won;
        this.message = message;
    }

    public boolean isWon() {
        return won;
    }

    public String getMessage() {
        return message;
    }
}
