package com.gusmurphy.chesses.rules.board.square;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class EmptySpot implements SpotState {

    public EmptySpot() {
    }

    @Override
    public Optional<Piece> occupyingPiece() {
        return Optional.empty();
    }

    @Override
    public Optional<Piece> pieceTakeableBy(Piece other) {
        return Optional.empty();
    }

    @Override
    public void onTurnChange(PlayerColor newTurnColor) {
    }

}
