package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.MovementStrategy;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Optional;

public class Judge {

    private BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public boolean moveIsPossible(Piece piece, BoardCoordinates move) {
        MovementStrategy movementStrategy = piece.movementStrategy();
        Optional<BoardCoordinates> optionalPiecePosition = boardState.coordinatesForPiece(piece);

        if (optionalPiecePosition.isPresent()) {
            if (movementStrategy.possibleMovesFrom(optionalPiecePosition.get()).contains(move)) {
                BoardCoordinates piecePosition = optionalPiecePosition.get();
                Direction directionOfMove = piecePosition.directionTo(move);
                BoardCoordinates spotToCheck = piecePosition;

                while (spotToCheck != move) {
                    spotToCheck = spotToCheck.coordinatesToThe(directionOfMove).get();

                    if (boardState.getPieceAt(spotToCheck).isPresent()) {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }

}
