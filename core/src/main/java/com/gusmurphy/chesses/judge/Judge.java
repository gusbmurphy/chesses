package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.Collections;
import java.util.List;

public class Judge {

    private final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public List<BoardCoordinates> movesFor(Piece piece) {
        if (boardState.coordinatesForPiece(piece.getPiece()).isPresent()) {
            return piece.getPiece().movementStrategy().possibleMovesFrom(piece.getCoordinates());
        }
        return Collections.emptyList();
    }

}
