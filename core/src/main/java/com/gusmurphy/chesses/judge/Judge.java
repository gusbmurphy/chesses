package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Optional;

public class Judge {

    private BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public boolean moveIsPossible(Piece piece, BoardCoordinates move) {
        Optional<BoardCoordinates> piecePosition = boardState.coordinatesForPiece(piece);

        return piecePosition
            .filter(coordinates -> piece.movementStrategy().possibleMovesFrom(coordinates).contains(move))
            .isPresent();
    }

}
