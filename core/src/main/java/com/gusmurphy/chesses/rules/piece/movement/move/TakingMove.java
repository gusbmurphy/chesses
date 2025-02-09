package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class TakingMove extends Move {

    private final Piece pieceToTake;

    public TakingMove(Piece movingPiece, UnassociatedMove move, Piece pieceToTake) {
        super(move, movingPiece);
        this.pieceToTake = pieceToTake;
    }

    @Override
    public Optional<UnassociatedMove> next() {
        return Optional.empty();
    }

    @Override
    public Optional<Piece> takes() {
        return Optional.of(pieceToTake);
    }

}
