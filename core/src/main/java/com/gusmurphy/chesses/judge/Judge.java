package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;

public class Judge {

    private BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public boolean moveIsPossible(Piece piece, BoardCoordinates move) {
        BoardCoordinates piecePosition = boardState.coordinatesForPiece(piece).get();
        return piece.movementStrategy().possibleMovesFrom(piecePosition).contains(move);
    }

}
