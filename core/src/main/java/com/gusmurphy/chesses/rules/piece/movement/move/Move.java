package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.Piece;

public class Move extends UnassociatedMoveDecorator {

    private final Piece movingPiece;

    public Move(UnassociatedMove move, Piece piece) {
        super(move);
        movingPiece = piece;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

    public Piece findMovingPieceOn(BoardState boardState) {
        return boardState
            .getAllPieces()
            .stream()
            .filter(piece -> piece.sameBoardIdAs(movingPiece))
            .findFirst()
            .get();
    }

}
