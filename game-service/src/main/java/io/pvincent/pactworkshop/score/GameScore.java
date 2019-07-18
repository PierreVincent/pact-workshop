package io.pvincent.pactworkshop.score;

public class GameScore {

    private final int played;
    private final int won;

    public GameScore(int played, int won) {
        this.played = played;
        this.won = won;
    }

    public int getPlayed() {
        return played;
    }

    public int getWon() {
        return won;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameScore gameScore = (GameScore) o;

        if (played != gameScore.played) return false;
        return won == gameScore.won;
    }

    @Override
    public int hashCode() {
        int result = played;
        result = 31 * result + won;
        return result;
    }

    @Override
    public String toString() {
        return "GameScore{" + "played=" + played + ", won=" + won + '}';
    }
}
