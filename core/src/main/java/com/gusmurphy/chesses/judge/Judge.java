package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.Board;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Judge {

    private final Board board;

    public Judge(Board board) {
        this.board = board;
    }

    public List<BoardCoordinates> movesFor(Piece piece) {
        Optional<BoardCoordinates> piecePosition = board.coordinatesForPiece(piece);
        if (piecePosition.isPresent()) {
            return piece.movementStrategy().possibleMovesFrom(piecePosition.get());
        }
        return Collections.emptyList();
    }

}
