package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class TakingMove implements Move {

    private final Coordinates spot;
    private final Piece pieceToTake;

    public TakingMove(Coordinates spot, Piece pieceToTake) {
        this.spot = spot;
        this.pieceToTake = pieceToTake;
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
        return Optional.of(pieceToTake);
    }
}
