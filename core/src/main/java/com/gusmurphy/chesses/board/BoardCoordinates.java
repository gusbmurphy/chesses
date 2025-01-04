package com.gusmurphy.chesses.board;

public class BoardCoordinates {
    private final File file;
    private final Rank rank;

    public BoardCoordinates(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public File file() {
        return file;
    }

    public Rank rank() {
        return rank;
    }
}
