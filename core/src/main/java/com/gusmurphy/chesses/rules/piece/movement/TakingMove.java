package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class TakingMove extends MoveDecorator {

    private final Piece pieceToTake;

    public TakingMove(Move move, Piece pieceToTake) {
        super(move);
        this.pieceToTake = pieceToTake;
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
