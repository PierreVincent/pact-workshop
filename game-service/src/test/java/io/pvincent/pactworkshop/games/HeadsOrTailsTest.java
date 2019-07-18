package io.pvincent.pactworkshop.games;

import org.junit.Assert;
import org.junit.Test;

public class HeadsOrTailsTest {

    @Test
    public void testThatHeadsAgainstHeadsWin() {
        HeadsOrTails game = new HeadsOrTails(() -> true);
        GameResult result = game.play(true);
        Assert.assertTrue(result.isWon());
        Assert.assertEquals("Heads: you won!", result.getMessage());
    }

    @Test
    public void testThatHeadsAgainstTailLoses() {
        HeadsOrTails game = new HeadsOrTails(() -> false);
        GameResult result = game.play(true);
        Assert.assertFalse(result.isWon());
        Assert.assertEquals("Tails: you lost!", result.getMessage());
    }

    @Test
    public void testThatTailAgainstTailWins() {
        HeadsOrTails game = new HeadsOrTails(() -> false);
        GameResult result = game.play(false);
        Assert.assertTrue(result.isWon());
        Assert.assertEquals("Tails: you won!", result.getMessage());
    }

    @Test
    public void testThatTailAgainstHeadsLoses() {
        HeadsOrTails game = new HeadsOrTails(() -> true);
        GameResult result = game.play(false);
        Assert.assertFalse(result.isWon());
        Assert.assertEquals("Heads: you lost!", result.getMessage());
    }

}
