package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.piece.Piece;

public class MoveWithPiece extends MoveDecorator {

    private final Piece movingPiece;

    public MoveWithPiece(Move move, Piece piece) {
        super(move);
        movingPiece = piece;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

}
