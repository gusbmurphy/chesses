package com.gusmurphy.chesses.rules.board.square;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.judge.TurnChangeListener;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceType;

import java.util.Optional;

public class EnPassantSpot implements SpotState, TurnChangeListener {

    private final Piece takeablePiece;
    private boolean expired = false;

    public EnPassantSpot(Piece takeablePiece) {
        this.takeablePiece = takeablePiece;
    }

    @Override
    public Optional<Piece> occupyingPiece() {
        return Optional.empty();
    }

    @Override
    public Optional<Piece> pieceTakeableBy(Piece other) {
        if (expired) {
            return Optional.empty();
        }

        if (other.color() != takeablePiece.color() && other.type() == PieceType.PAWN) {
            return Optional.of(takeablePiece);
        }

        return Optional.empty();
    }

    @Override
    public void onTurnChange(PlayerColor newTurnColor) {
        expired = true;
    }

}
