package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.PieceEventListener;
import com.gusmurphy.chesses.rules.piece.Piece;

public abstract class PieceAwareMovementStrategy implements MovementStrategy, PieceEventListener {

    protected Piece relevantPiece;

    public void setRelevantPiece(Piece piece) {
        relevantPiece = piece;
        piece.subscribeToEvents(this);
    }

}
