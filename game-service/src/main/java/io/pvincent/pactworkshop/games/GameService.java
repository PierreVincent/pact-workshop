package io.pvincent.pactworkshop.games;

import java.security.SecureRandom;
import java.util.Random;

public class GameService {

    private final Random RNG = new SecureRandom();
    private final HeadsOrTails headsOrTails = new HeadsOrTails(RNG::nextBoolean);
    private final FourTwentyOne fourTwentyOne = new FourTwentyOne(() -> RNG.nextInt(6) + 1);

    public GameResult playGame(String game, String choice) throws UnkownGameException {
        switch (game.toLowerCase()) {
            case "fourtwentyone":
                return fourTwentyOne.play();
            case "headsortails":
                return headsOrTails.play("heads".equalsIgnoreCase(choice));
            default:
                throw new UnkownGameException(game);
        }
    }

}
