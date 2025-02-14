package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class StaticMove implements UnassociatedMove {
    private final Coordinates spot;

    public StaticMove(Coordinates spot) {
        this.spot = spot;
    }

    @Override
    public Coordinates spot() {
        return spot;
    }

    @Override
    public Optional<UnassociatedMove> next() {
        return Optional.empty();
    }

    @Override
    public Optional<Piece> takes() {
        return Optional.empty();
    }

    @Override
    public boolean mustTake() {
        return false;
    }
}
