package com.gusmurphy.chesses.board;

import java.util.Arrays;
import java.util.List;

public enum Direction {

    N(1, 0),
    NE(1, 1),
    E(0, 1),
    SE(-1, 1),
    S(-1, 0),
    SW(-1, -1),
    W(0, -1),
    NW(1, -1);

    private final int verticalValue;
    private final int horizontalValue;

    Direction (int verticalValue, int horizontalValue) {
        this.verticalValue = verticalValue;
        this.horizontalValue = horizontalValue;
    }

    public static List<Direction> every() {
        return Arrays.asList(values());
    }

    public int verticalValue() {
        return verticalValue;
    }

    public int horizontalValue() {
        return horizontalValue;
    }

}
