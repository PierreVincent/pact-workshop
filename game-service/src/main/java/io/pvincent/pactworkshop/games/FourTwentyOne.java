package io.pvincent.pactworkshop.games;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FourTwentyOne {

    private static final List<Integer> WINNING_NUMBERS = Arrays.asList(4, 2, 1);

    private final Supplier<Integer> die;

    public FourTwentyOne(Supplier<Integer> die) {
        this.die = die;
    }

    public GameResult play() {
        List<Integer> roll = Arrays.asList(die.get(), die.get(), die.get());
        boolean won = roll.containsAll(WINNING_NUMBERS);
        String message = "You rolled " + roll.stream().map(String::valueOf).collect(Collectors.joining("-"));
        return new GameResult(won, message);
    }

}