package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class StaticMove implements Move {
    private final Coordinates spot;

    public StaticMove(Coordinates spot) {
        this.spot = spot;
    }

    @Override
    public Coordinates spot() {
        return spot;
    }

    @Override
    public Optional<Move> next() {
        return Optional.empty();
    }

    @Override
    public Optional<Piece> takes() {
        return Optional.empty();
    }
}
