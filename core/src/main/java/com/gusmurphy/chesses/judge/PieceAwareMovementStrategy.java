package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardStateEventListener;
import com.gusmurphy.chesses.piece.Piece;

public abstract class PieceAwareMovementStrategy implements MovementStrategy, BoardStateEventListener {

    protected Piece relevantPiece;

    public void setRelevantPiece(Piece piece) {
        relevantPiece = piece;
    }

}
