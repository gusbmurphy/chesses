package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.Piece;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.PieceColorAndMovement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Judge {

    private final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public List<BoardCoordinates> movesFor(PieceColorAndMovement pieceColorAndMovement) {
        Optional<BoardCoordinates> piecePosition = boardState.coordinatesForPiece(pieceColorAndMovement);
        if (piecePosition.isPresent()) {
            Piece piece = new Piece(pieceColorAndMovement, piecePosition.get());
            return movesFor(piece);
        }
        return Collections.emptyList();
    }

    public List<BoardCoordinates> movesFor(Piece piece) {
        return piece.getPiece().movementStrategy().possibleMovesFrom(piece.getCoordinates());
    }

}
