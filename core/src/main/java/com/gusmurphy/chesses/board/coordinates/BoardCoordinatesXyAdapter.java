package com.gusmurphy.chesses.board.coordinates;

public class BoardCoordinatesXyAdapter {

    private final int x;
    private final int y;

    public BoardCoordinatesXyAdapter(BoardCoordinates coordinates) {
        this.x = coordinates.file.ordinal();
        this.y = coordinates.rank.ordinal();
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

}
