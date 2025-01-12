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
import java.util.List;
import java.util.stream.Stream;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @ParameterizedTest
    @MethodSource("singleSpotMoves")
    void aSimpleLinearMovementStrategyLimitsMovementToOneDirection(
        Direction direction, BoardCoordinates expected
    ) {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(direction), 1);
        Piece piece = new Piece(linear, D4);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);

        List<BoardCoordinates> possibleMoves = judge.movesFor(piece);
        assertEquals(Collections.singletonList(expected), possibleMoves);
    }

    private static Stream<Arguments> singleSpotMoves() {
        return Stream.of(
            Arguments.of(Direction.N, D5),
            Arguments.of(Direction.NE, E5),
            Arguments.of(Direction.E, E4),
            Arguments.of(Direction.SE, E3),
            Arguments.of(Direction.S, D3),
            Arguments.of(Direction.SW, C3),
            Arguments.of(Direction.W, C4),
            Arguments.of(Direction.NW, C5)
        );
    }

    @ParameterizedTest
    @MethodSource("movesOffTheBoard")
    void anEmptyListIsReturnedIfTheOnlyMoveIsOffTheBoard(BoardCoordinates from, Direction direction) {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(direction), 1);
        Piece piece = new Piece(linear, from);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);

        List<BoardCoordinates> possibleMoves = judge.movesFor(piece);
        assertEquals(0, possibleMoves.size());
    }

    private static Stream<Arguments> movesOffTheBoard() {
        return Stream.of(
            Arguments.of(A8, Direction.N),
            Arguments.of(A8, Direction.W),
            Arguments.of(H8, Direction.N),
            Arguments.of(H8, Direction.E),
            Arguments.of(H1, Direction.S),
            Arguments.of(H1, Direction.E),
            Arguments.of(A1, Direction.S),
            Arguments.of(A1, Direction.W)
        );
    }

    @Test
    void weCouldAlsoMoveTwoSpotsInOneDirection() {
        MovementStrategy strategy = new LinearMovementStrategy(Collections.singletonList(Direction.N), 2);
        Piece piece = new Piece(strategy, B2);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        List<BoardCoordinates> possibleMoves = judge.movesFor(piece);

        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.containsAll(Arrays.asList(B3, B4)));
    }

    @Test
    void aLinearStrategyWithNoMaxDistanceCanMoveAllTheWayAcrossTheBoard() {
        MovementStrategy strategy = new LinearMovementStrategy(Collections.singletonList(Direction.N));
        Piece piece = new Piece(strategy, B1);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        List<BoardCoordinates> possibleMoves = judge.movesFor(piece);

        assertEquals(7, possibleMoves.size());
        assertTrue(possibleMoves.containsAll(Arrays.asList(B2, B3, B4, B5, B6, B7, B8)));
    }

    @Test
    void andEvenInMultipleDirections() {
        MovementStrategy strategy = new LinearMovementStrategy(Arrays.asList(Direction.N, Direction.S), 1);
        Piece piece = new Piece(strategy, B2);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        List<BoardCoordinates> possibleMoves = judge.movesFor(piece);
        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.containsAll(Arrays.asList(B3, B1)));
    }

}
