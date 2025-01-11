package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Judge {

    private final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public List<BoardCoordinates> movesFor(Piece piece) {
        Optional<BoardCoordinates> piecePosition = boardState.coordinatesForPiece(piece);
        if (piecePosition.isPresent()) {
            return piece.movementStrategy().possibleMovesFrom(piecePosition.get());
        }
        return Collections.emptyList();
    }

}
