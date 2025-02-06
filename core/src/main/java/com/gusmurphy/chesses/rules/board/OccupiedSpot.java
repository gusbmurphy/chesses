package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class OccupiedSpot implements SpotState {

    private final Piece occupyingPiece;

    public OccupiedSpot(Piece occupyingPiece) {
        this.occupyingPiece = occupyingPiece;
    }

    @Override
    public Optional<Piece> occupyingPiece() {
        return Optional.of(occupyingPiece);
    }

    @Override
    public Optional<Piece> pieceTakeableBy(Piece other) {
        if (occupyingPiece.color() != other.color()) {
            return Optional.of(occupyingPiece);
        }
        return Optional.empty();
    }
}
