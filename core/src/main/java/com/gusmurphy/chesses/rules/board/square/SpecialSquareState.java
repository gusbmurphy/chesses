package com.gusmurphy.chesses.rules.board.square;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;

public class SpecialSquareState {

    private final Coordinates coordinates;

    public SpecialSquareState(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates coordinates() {
        return coordinates;
    }

}
