package io.pvincent.pactworkshop.games;

public class UnkownGameException extends Exception {

    public UnkownGameException(String game) {
        super("Unknown game: "+game);
    }
}
