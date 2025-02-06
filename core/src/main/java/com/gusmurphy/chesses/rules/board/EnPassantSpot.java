package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceType;

import java.util.Optional;

public class EnPassantSpot implements SpotState {

    private final Piece takeablePiece;

    public EnPassantSpot(Piece takeablePiece) {
        this.takeablePiece = takeablePiece;
    }

    @Override
    public Optional<Piece> occupyingPiece() {
        return Optional.empty();
    }

    @Override
    public Optional<Piece> pieceTakeableBy(Piece other) {
        if (other.color() != takeablePiece.color() && other.type() == PieceType.PAWN) {
            return Optional.of(takeablePiece);
        }
        return Optional.empty();
    }

}
