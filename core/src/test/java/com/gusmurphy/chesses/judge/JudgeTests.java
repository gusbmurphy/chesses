package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.piece.LinearMovementStrategy;
import com.gusmurphy.chesses.piece.MovementStrategy;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.player.PlayerColor;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JudgeTests {

    @Test
    void aPieceWithALinearMovementStrategyCanMoveToAnUnobstructedPositionInItsStrategy() {
        MovementStrategy movementStrategy = new LinearMovementStrategy(Collections.singletonList(Direction.N), 1);
        Piece piece = new Piece(PlayerColor.BLACK, movementStrategy);

        BoardState boardState = new BoardState();
        boardState.placePieceAt(piece, A2);

        Judge judge = new Judge(boardState);

        assertTrue(judge.moveIsPossible(piece, A3));
    }

    @Test
    void aPieceWithALinearMovementStrategyCannotMoveToAPositionNotInItsStrategy() {
        MovementStrategy movementStrategy = new LinearMovementStrategy(Collections.singletonList(Direction.N), 1);
        Piece piece = new Piece(PlayerColor.BLACK, movementStrategy);

        BoardState boardState = new BoardState();
        boardState.placePieceAt(piece, A2);

        Judge judge = new Judge(boardState);

        assertFalse(judge.moveIsPossible(piece, A4));
    }

    @Test
    void noMoveIsPossibleForAPieceNotOnTheBoard() {
        BoardState boardState = new BoardState();
        Piece piece = new Piece();
        Judge judge = new Judge(boardState);
        assertFalse(judge.moveIsPossible(piece, A5));
    }

    @Test
    void aPieceWithALinearStrategyCannotMovePastAnotherPiece() {
        MovementStrategy movementStrategy = new LinearMovementStrategy(Collections.singletonList(Direction.N), 5);
        Piece piece = new Piece(PlayerColor.BLACK, movementStrategy);
        Piece blockingPiece = new Piece();

        BoardState boardState = new BoardState();
        boardState.placePieceAt(piece, A2);
        boardState.placePieceAt(blockingPiece, A4);

        Judge judge = new Judge(boardState);

        assertFalse(judge.moveIsPossible(piece, A5));
    }

}
