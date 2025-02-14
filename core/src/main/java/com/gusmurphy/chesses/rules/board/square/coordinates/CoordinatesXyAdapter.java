package com.gusmurphy.chesses.rules.board.square.coordinates;

import java.util.Arrays;

public class CoordinatesXyAdapter {

    private final int x;
    private final int y;
    private final Coordinates coordinates;

    public CoordinatesXyAdapter(Coordinates coordinates) {
        this.x = coordinates.file.ordinal();
        this.y = coordinates.rank.ordinal();
        this.coordinates = coordinates;
    }

    public CoordinatesXyAdapter(int x, int y) {
        this.x = x;
        this.y = y;

        coordinates = Arrays.stream(Coordinates.values())
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

    public Coordinates coordinates() {
        return coordinates;
    }

}
