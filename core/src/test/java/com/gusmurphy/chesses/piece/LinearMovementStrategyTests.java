package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.gusmurphy.chesses.board.BoardCoordinates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinearMovementStrategyTests {

    @ParameterizedTest
    @MethodSource("provideDirectionsForLinear")
    void aSimpleLinearMovementStrategyLimitsMovementToOneDirection(
        Direction direction, BoardCoordinates expected
    ) {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(direction), 1);
        List<BoardCoordinates> possibleMoves = linear.possibleMovesFrom(D4);
        assertEquals(Collections.singletonList(expected), possibleMoves);
    }

    private static Stream<Arguments> provideDirectionsForLinear() {
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
        List<BoardCoordinates> possibleMoves = linear.possibleMovesFrom(from);
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
        List<BoardCoordinates> possibleMoves = strategy.possibleMovesFrom(B2);
        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.containsAll(Arrays.asList(B3, B4)));
    }

}
