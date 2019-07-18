package io.pvincent.pactworkshop.games;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FourTwentyOneTest {

    private class MockDie implements Supplier<Integer> {
        private final List<Integer> rolls;
        private int rollCount = 0;

        MockDie(Integer... rolls) {
            this.rolls = Arrays.asList(rolls);
        }

        @Override
        public Integer get() {
            Integer roll = this.rolls.get(this.rollCount);
            this.rollCount = (this.rollCount + 1) % this.rolls.size();
            return roll;
        }
    }

    @Test
    public void testThat421IsAWinner() {
        FourTwentyOne game = new FourTwentyOne(new MockDie(4,2,1));
        GameResult result = game.play();

        Assert.assertTrue(result.isWon());
        Assert.assertEquals("You rolled 4-2-1", result.getMessage());
    }

    @Test
    public void testThat124IsAWinner() {
        FourTwentyOne game = new FourTwentyOne(new MockDie(1,2,4));
        GameResult result = game.play();

        Assert.assertTrue(result.isWon());
        Assert.assertEquals("You rolled 1-2-4", result.getMessage());
    }

    @Test
    public void testThat214IsAWinner() {
        FourTwentyOne game = new FourTwentyOne(new MockDie(2,1,4));
        GameResult result = game.play();

        Assert.assertTrue(result.isWon());
        Assert.assertEquals("You rolled 2-1-4", result.getMessage());
    }

    @Test
    public void testThat241IsAWinner() {
        FourTwentyOne game = new FourTwentyOne(new MockDie(2,4,1));
        GameResult result = game.play();

        Assert.assertTrue(result.isWon());
        Assert.assertEquals("You rolled 2-4-1", result.getMessage());
    }

    @Test
    public void testThat142IsAWinner() {
        FourTwentyOne game = new FourTwentyOne(new MockDie(1,4,2));
        GameResult result = game.play();

        Assert.assertTrue(result.isWon());
        Assert.assertEquals("You rolled 1-4-2", result.getMessage());
    }

    @Test
    public void testThat111IsALoser() {
        FourTwentyOne game = new FourTwentyOne(new MockDie(1,1,1));
        GameResult result = game.play();

        Assert.assertFalse(result.isWon());
        Assert.assertEquals("You rolled 1-1-1", result.getMessage());
    }

    @Test
    public void testThat123IsALoser() {
        FourTwentyOne game = new FourTwentyOne(new MockDie(1,2,3));
        GameResult result = game.play();

        Assert.assertFalse(result.isWon());
        Assert.assertEquals("You rolled 1-2-3", result.getMessage());
    }

}
