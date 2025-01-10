package com.gusmurphy.chesses.board.coordinates;

import java.util.Arrays;

public class BoardCoordinatesXyAdapter {

    private final int x;
    private final int y;
    private final BoardCoordinates coordinates;

    public BoardCoordinatesXyAdapter(BoardCoordinates coordinates) {
        this.x = coordinates.file.ordinal();
        this.y = coordinates.rank.ordinal();
        this.coordinates = coordinates;
    }

    public BoardCoordinatesXyAdapter(int x, int y) {
        this.x = x;
        this.y = y;

        coordinates = Arrays.stream(BoardCoordinates.values())
            .filter(c -> c.file.ordinal() == x)
            .filter(c -> c.rank.ordinal() == y)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Couldn't derive board coordinates from (" + x + ", " + y + ")"));
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public BoardCoordinates coordinates() {
        return coordinates;
    }

}
