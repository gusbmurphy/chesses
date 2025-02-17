package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.*;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.move.StaticMove;
import com.gusmurphy.chesses.rules.piece.movement.strategy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static com.gusmurphy.chesses.rules.board.Direction.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JudgeTests {

    @Test
    void weCanGetAllMovesForAllPiecesOnTheBoard() {
        Piece king = DefaultPieces.king(WHITE, C4);
        Piece rook = DefaultPieces.rook(WHITE, G3);
        BoardState boardState = new BoardState(king, rook);

        Judge judge = new Judge(boardState);
        List<Move> moves = judge.getPossibleMoves();

        List<Move> movesForKing = moves.stream().filter(move -> move.getMovingPiece() == king).collect(Collectors.toList());
        List<Move> movesForRook = moves.stream().filter(move -> move.getMovingPiece() == rook).collect(Collectors.toList());

        assertEquals(8, movesForKing.size());
        assertEquals(14, movesForRook.size());
    }

    @Test
    void weCanGetMovesForJustOnePiece() {
        Piece king = DefaultPieces.king(WHITE, C4);
        Piece rook = DefaultPieces.rook(WHITE, G3);
        BoardState boardState = new BoardState(king, rook);

        Judge judge = new Judge(boardState);
        List<Move> moves =
            judge.getPossibleMoves().stream().filter(move -> move.getMovingPiece() == king).collect(Collectors.toList());

        assertEquals(8, moves.size());
    }

    @ParameterizedTest
    @MethodSource("okayMoves")
    void aPieceWithALinearMovementStrategyCanMoveToAnUnobstructedPositionInItsStrategy(Coordinates coordinates) {
        MovementStrategy movementStrategy = new LinearMovementStrategy(Arrays.asList(Direction.values()), 1);
        Piece piece = new PieceBuilder()
            .color(BLACK)
            .movementStrategy(movementStrategy)
            .startingCoordinates(D4)
            .type(PieceType.KING)
            .build();

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);

        assertTrue(judge.getPossibleMoves().stream().anyMatch(m -> m.coordinates() == coordinates));
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
        Piece piece = new PieceBuilder()
            .color(BLACK)
            .movementStrategy(movementStrategy)
            .startingCoordinates(A2)
            .type(PieceType.KING)
            .build();

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);

        assertFalse(judge.getPossibleMoves().stream().anyMatch(m -> m.coordinates() == A4));
    }

    @Test
    void noMoveIsPossibleWithNoPiecesOnTheBoard() {
        BoardState boardState = new BoardState();
        Judge judge = new Judge(boardState);
        assertTrue(judge.getPossibleMoves().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("blockedMoves")
    void aPieceCannotMovePastAnotherPieceOfTheSameColor(
        Coordinates otherPiecePosition, Coordinates coordinates, PlayerColor color
    ) {
        MovementStrategy movementStrategy = new LinearMovementStrategy(
            Arrays.asList(Direction.N, Direction.E), 5
        );
        Piece piece = new PieceBuilder()
            .color(color)
            .movementStrategy(movementStrategy)
            .startingCoordinates(A2)
            .type(PieceType.KING)
            .build();

        Piece blockingPiece = new PieceBuilder()
            .color(color)
            .startingCoordinates(otherPiecePosition)
            .build();

        BoardState boardState = new BoardState();
        boardState.place(piece);
        boardState.place(blockingPiece);

        Judge judge = new Judge(boardState);

        assertFalse(judge.getPossibleMoves().stream().anyMatch(m -> m.coordinates() == coordinates));
    }

    private static Stream<Arguments> blockedMoves() {
        return Stream.of(
            Arguments.of(A4, A5, WHITE),
            Arguments.of(B2, C2, BLACK)
        );
    }

    @ParameterizedTest
    @MethodSource("singlecoordinatesMoves")
    void aSimpleLinearMovementStrategyLimitsMovementToOneDirection(
        Direction direction, Coordinates expected
    ) {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(direction), 1);
        Piece piece = new Piece(linear, D4);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);

        List<Move> possibleMoves = judge.getPossibleMoves();
        assertEquals(expected, possibleMoves.get(0).coordinates());
    }

    private static Stream<Arguments> singlecoordinatesMoves() {
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
    void anEmptyListIsReturnedIfTheOnlyMoveIsOffTheBoard(Coordinates from, Direction direction) {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(direction), 1);
        Piece piece = new Piece(linear, from);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);

        List<Move> possibleMoves = judge.getPossibleMoves();
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
    void weCouldAlsoMoveTwocoordinatessInOneDirection() {
        MovementStrategy strategy = new LinearMovementStrategy(Collections.singletonList(Direction.N), 2);
        Piece piece = new Piece(strategy, B2);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        List<Move> possibleMoves = judge.getPossibleMoves();

        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B3));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B4));
    }

    @Test
    void aLinearStrategyWithNoMaxDistanceCanMoveAllTheWayAcrossTheBoard() {
        MovementStrategy strategy = new LinearMovementStrategy(Collections.singletonList(Direction.N));
        Piece piece = new Piece(strategy, B1);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        List<Move> possibleMoves = judge.getPossibleMoves();

        assertEquals(7, possibleMoves.size());
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B2));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B3));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B4));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B5));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B6));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B7));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B8));
    }

    @Test
    void andEvenInMultipleDirections() {
        MovementStrategy strategy = new LinearMovementStrategy(Arrays.asList(Direction.N, Direction.S), 1);
        Piece piece = new Piece(strategy, B2);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        List<Move> possibleMoves = judge.getPossibleMoves();
        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B3));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == B1));
    }

    @Test
    void aRelativeStrategyOnlyGoesToCertaincoordinatessRelativeToTheCurrentOne() {
        RelativeMovementStrategy partOne = new RelativeMovementStrategy(1, 2);
        RelativeMovementStrategy partTwo = new RelativeMovementStrategy(-2, 3);
        MovementStrategy fullStrategy = new RelativeMovementStrategy(partOne, partTwo);

        Piece piece = new Piece(fullStrategy, E4);
        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        List<Move> possibleMoves = judge.getPossibleMoves();

        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == F6));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == C7));
    }

    @Test
    void aRelativeStrategyCannotMoveOffTheBoard() {
        MovementStrategy strategy = new RelativeMovementStrategy(0, 1);
        Piece piece = new Piece(strategy, A8);
        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        List<Move> possibleMoves = judge.getPossibleMoves();

        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    void pawnsCanOnlyMoveTwoSquaresOnTheFirstMove() {
        Piece pawn = DefaultPieces.pawn(WHITE, C2);
        BoardState boardState = new BoardState();
        boardState.place(pawn);

        Judge judge = new Judge(boardState);
        List<Move> possibleMoves = judge.getPossibleMoves();

        // At first, we can move up to two spaces...
        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == C3));
        assertTrue(possibleMoves.stream().anyMatch(m -> m.coordinates() == C4));

        // After moving, there should only be one possible move.
        judge.submitMove(pawn, C4);
        List<Move> possibleMovesAfterFirst = judge.getPossibleMoves();
        assertEquals(1, possibleMovesAfterFirst.size());
        assertTrue(possibleMovesAfterFirst.stream().anyMatch(m -> m.coordinates() == C5));
    }

    @Test
    void weCanAlsoHaveMovementThatOnlyAllowsForTakingPieces() {
        MovementStrategy strategy = new TakeOnlyMovementStrategy(
            new LinearMovementStrategy(Arrays.asList(N, S), 1)
        );
        Piece takingPiece = new PieceBuilder()
            .color(WHITE)
            .movementStrategy(strategy)
            .startingCoordinates(D3)
            .build();
        Piece someOtherPiece = new PieceBuilder()
            .color(BLACK)
            .startingCoordinates(D4)
            .build();

        Judge judge = new Judge(new BoardState(takingPiece, someOtherPiece));
        List<Move> possibleMoves = judge.getPossibleMoves();

        // The only possible move should be the one to take the other piece
        assertEquals(1, possibleMoves.size());
        Move move = possibleMoves.get(0);
        assertEquals(D4, move.coordinates());
        assertEquals(someOtherPiece, move.takes().get());
        assertEquals(takingPiece, move.getMovingPiece());
    }

    @Test
    void someMovesCantTakePieces() {
        MovementStrategy strategy = new NoTakeMovementStrategy(
            new LinearMovementStrategy(Arrays.asList(N, S), 1)
        );
        Piece takingPiece = new PieceBuilder()
            .color(WHITE)
            .movementStrategy(strategy)
            .startingCoordinates(D3)
            .build();
        Piece someOtherPiece = new PieceBuilder()
            .color(BLACK)
            .startingCoordinates(D4)
            .build();

        Judge judge = new Judge(new BoardState(takingPiece, someOtherPiece));
        List<Move> possibleMoves = judge.getPossibleMoves();

        // The only possible move should be the one to take the other piece
        assertEquals(1, possibleMoves.size());
        Move move = possibleMoves.get(0);
        assertEquals(D2, move.coordinates());
    }

    @ParameterizedTest
    @MethodSource("oppositeColorPairs")
    void aMoveOntoAcoordinatesOccupiedByAnotherPieceOfTheOppositeColorTakesThatPiece(PlayerColor takingColor, PlayerColor takenColor) {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(S), 1);

        Piece piece = new PieceBuilder()
            .color(takingColor)
            .movementStrategy(linear)
            .startingCoordinates(C4)
            .build();
        Piece pieceToTake = new PieceBuilder()
            .color(takenColor)
            .startingCoordinates(C3)
            .build();

        BoardState boardState = new BoardState();
        boardState.place(piece);
        boardState.place(pieceToTake);

        Judge judge = new Judge(boardState);

        List<Move> moves = judge.getPossibleMoves();
        assertEquals(pieceToTake, moves.get(0).takes().get());
    }

    @Test
    void aRookCanTakeAPawnAWaysAway() {
        Piece rook = DefaultPieces.rook(BLACK, B2);
        Piece pawn = DefaultPieces.pawn(WHITE, B7);

        BoardState boardState = new BoardState(rook, pawn);
        Judge judge = new Judge(boardState);

        List<Move> moves = judge.getPossibleMoves();
        assertTrue(moves.stream().anyMatch(move -> move.coordinates() == B7 && move.takes().get() == pawn));
    }

    @Test
    void afterAPieceIsTakenItIsNoLongerOnTheBoard() {
        Piece piece = DefaultPieces.rook(WHITE, C4);
        Piece pieceToTake = DefaultPieces.rook(BLACK, C5);

        BoardState boardState = new BoardState();
        boardState.place(piece);
        boardState.place(pieceToTake);

        Judge judge = new Judge(boardState);
        judge.submitMove(piece, C5);

        assertFalse(boardState.getAllPieces().contains(pieceToTake));
    }

    @Test
    void whenAPieceIsTakenAnEventIsBroadcast() {
        Piece piece = DefaultPieces.rook(WHITE, C4);
        Piece pieceToTake = DefaultPieces.rook(BLACK, C5);

        BoardState boardState = new BoardState();
        boardState.place(piece);
        boardState.place(pieceToTake);

        Judge judge = new Judge(boardState);

        TestPieceEventListener listener = new TestPieceEventListener();
        pieceToTake.subscribeToEvents(listener);

        judge.submitMove(piece, C5);

        assertEquals(pieceToTake, listener.getLastPieceTaken().get());
    }

    @Test
    void aMoveCanBeSubmittedToMoveAPieceOnTheBoard() {
        Piece piece = DefaultPieces.rook(WHITE, C4);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        judge.submitMove(piece, C5);

        assertEquals(C5, piece.getCoordinates());
    }

    @Test
    void anIllegalMoveDoesNothing() {
        Piece piece = DefaultPieces.rook(WHITE, C4);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        Judge judge = new Judge(boardState);
        judge.submitMove(piece, D5);

        assertEquals(C4, piece.getCoordinates());
    }

    private static Stream<Arguments> oppositeColorPairs() {
        return Stream.of(
            Arguments.of(PlayerColor.WHITE, PlayerColor.BLACK),
            Arguments.of(PlayerColor.BLACK, PlayerColor.WHITE)
        );
    }

    @Test
    void aMoveCanMoveAnotherPiece() {
        MovementStrategy base = new LinearMovementStrategy(Collections.singletonList(N), 1);
        Piece otherPiece = new Piece(new NullMovementStrategy(), B2);
        MovementStrategy linkedStrategy = new LinkedMovementStrategy(
            base,
            new Move(new StaticMove(D5), otherPiece)
        );

        Piece targetPiece = new Piece(linkedStrategy, B3);
        Judge judge = new Judge(new BoardState(otherPiece, targetPiece));

        judge.submitMove(targetPiece, B4);

        assertEquals(targetPiece.getCoordinates(), B4);
        assertEquals(otherPiece.getCoordinates(), D5);
    }

}
