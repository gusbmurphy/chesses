package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.piece.Piece;

public class PieceMove extends MoveDecorator {

    private final Piece movingPiece;

    public PieceMove(UnassociatedMove move, Piece piece) {
        super(move);
        movingPiece = piece;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

}
