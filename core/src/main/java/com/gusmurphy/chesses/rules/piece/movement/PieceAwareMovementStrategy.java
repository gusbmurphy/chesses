package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.BoardStateEventListener;
import com.gusmurphy.chesses.rules.piece.Piece;

public abstract class PieceAwareMovementStrategy implements MovementStrategy, BoardStateEventListener {

    protected Piece relevantPiece;

    public void setRelevantPiece(Piece piece) {
        relevantPiece = piece;
    }

}
