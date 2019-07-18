package io.pvincent.pactworkshop.games;

import java.util.function.Supplier;

public class HeadsOrTails {

    private final Supplier<Boolean> coin;

    public HeadsOrTails(Supplier<Boolean> coin) {
        this.coin = coin;
    }

    public GameResult play(boolean heads) {
        boolean flipHeads = coin.get();
        boolean won = (flipHeads == heads);
        String message = (flipHeads ? "Heads" : "Tails") + ": you " + (won ? "won" : "lost") + "!";
        return new GameResult(won, message);
    }

}