package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.piece.*;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.player.PlayerColor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JudgeTests {

    @ParameterizedTest
    @MethodSource("okayMoves")
    void aPieceWithALinearMovementStrategyCanMoveToAnUnobstructedPositionInItsStrategy(BoardCoordinates move) {
        MovementStrategy movementStrategy = new LinearMovementStrategy(Arrays.asList(Direction.values()), 1);
        PieceColorAndMovement pieceColorAndMovement = new PieceColorAndMovement(PlayerColor.BLACK, movementStrategy);
        Piece piece = new Piece(pieceColorAndMovement, D4, PieceType.KING);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);

        assertTrue(judge.movesFor(piece).contains(move));
    }

    private static Stream<Arguments> okayMoves() {
        return Stream.of(
            Arguments.of(D5),
            Arguments.of(E5),
            Arguments.of(E4),
            Arguments.of(E3),
            Arguments.of(D3),
            Arguments.of(C3),
            Arguments.of(C4),
            Arguments.of(C5)
        );
    }

    @Test
    void aPieceWithALinearMovementStrategyCannotMoveToAPositionNotInItsStrategy() {
        MovementStrategy movementStrategy = new LinearMovementStrategy(Collections.singletonList(Direction.N), 1);
        PieceColorAndMovement pieceColorAndMovement = new PieceColorAndMovement(PlayerColor.BLACK, movementStrategy);
        Piece piece = new Piece(pieceColorAndMovement, A2, PieceType.KING);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);

        assertFalse(judge.movesFor(piece).contains(A4));
    }

    @Test
    void noMoveIsPossibleForAPieceNotOnTheBoard() {
        BoardState boardState = new BoardState();
        PieceColorAndMovement pieceColorAndMovement = new PieceColorAndMovement();
        Piece piece = new Piece(pieceColorAndMovement, A4, PieceType.KING);
        Judge judge = new Judge(boardState);
        assertTrue(judge.movesFor(piece).isEmpty());
    }

    @Disabled("Waiting a moment to get moves running in relation to other pieces")
    @ParameterizedTest
    @MethodSource("blockedMoves")
    void aPieceWithALinearStrategyCannotMovePastAnotherPiece(BoardCoordinates otherPiecePosition, BoardCoordinates move) {
        MovementStrategy movementStrategy = new LinearMovementStrategy(
            Arrays.asList(Direction.N, Direction.E), 5
        );
        PieceColorAndMovement pieceColorAndMovement = new PieceColorAndMovement(PlayerColor.BLACK, movementStrategy);
        Piece piece = new Piece(pieceColorAndMovement, A2, PieceType.KING);

        PieceColorAndMovement blockingPieceColorAndMovement = new PieceColorAndMovement();
        Piece blockingPiece = new Piece(blockingPieceColorAndMovement, otherPiecePosition, PieceType.KING);

        BoardState boardState = new BoardState();
        boardState.place(piece);
        boardState.place(blockingPiece);

        Judge judge = new Judge(boardState);

        assertFalse(judge.movesFor(piece).contains(move));
    }

    private static Stream<Arguments> blockedMoves() {
        return Stream.of(
            Arguments.of(A4, A5),
            Arguments.of(B2, C2)
        );
    }

}
