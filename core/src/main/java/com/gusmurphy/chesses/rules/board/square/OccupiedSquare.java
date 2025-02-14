package com.gusmurphy.chesses.rules.board.square;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class OccupiedSquare implements SquareState {

    private final Piece occupyingPiece;

    public OccupiedSquare(Piece occupyingPiece) {
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

    @Override
    public void onTurnChange(PlayerColor newTurnColor) {
    }

}
