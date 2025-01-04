package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.File;
import com.gusmurphy.chesses.board.Rank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class MovementStrategyTests {

    @ParameterizedTest
    @MethodSource("provideDirectionsForLinear")
    void aSimpleLinearMovementStrategyLimitsMovementToOneDirection(
        Direction direction, BoardCoordinates expected
    ) {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(direction), 1);
        List<BoardCoordinates> possibleMoves = linear.possibleMovesFrom(new BoardCoordinates(File.D, Rank.FOUR));
        Assertions.assertEquals(Collections.singletonList(expected), possibleMoves);
    }

    @ParameterizedTest
    @MethodSource("movesOffTheBoard")
    void anEmptyListIsReturnedIfTheOnlyMoveIsOffTheBoard(BoardCoordinates from, Direction direction) {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(direction), 1);
        List<BoardCoordinates> possibleMoves = linear.possibleMovesFrom(from);
        Assertions.assertEquals(0, possibleMoves.size());
    }

    private static Stream<Arguments> movesOffTheBoard() {
        return Stream.of(
            Arguments.of(new BoardCoordinates(File.A, Rank.EIGHT), Direction.N),
            Arguments.of(new BoardCoordinates(File.A, Rank.EIGHT), Direction.W),
            Arguments.of(new BoardCoordinates(File.H, Rank.EIGHT), Direction.N),
            Arguments.of(new BoardCoordinates(File.H, Rank.EIGHT), Direction.E),
            Arguments.of(new BoardCoordinates(File.H, Rank.ONE), Direction.S),
            Arguments.of(new BoardCoordinates(File.H, Rank.ONE), Direction.E),
            Arguments.of(new BoardCoordinates(File.A, Rank.ONE), Direction.S),
            Arguments.of(new BoardCoordinates(File.A, Rank.ONE), Direction.W)
        );
    }

    private static Stream<Arguments> provideDirectionsForLinear() {
        return Stream.of(
            Arguments.of(
                Direction.N,
                new BoardCoordinates(File.D, Rank.FIVE)
            ),
            Arguments.of(
                Direction.NE,
                new BoardCoordinates(File.E, Rank.FIVE)
            ),
            Arguments.of(
                Direction.E,
                new BoardCoordinates(File.E, Rank.FOUR)
            ),
            Arguments.of(
                Direction.SE,
                new BoardCoordinates(File.E, Rank.THREE)
            ),
            Arguments.of(
                Direction.S,
                new BoardCoordinates(File.D, Rank.THREE)
            ),
            Arguments.of(
                Direction.SW,
                new BoardCoordinates(File.C, Rank.THREE)
            ),
            Arguments.of(
                Direction.W,
                new BoardCoordinates(File.C, Rank.FOUR)
            ),
            Arguments.of(
                Direction.NW,
                new BoardCoordinates(File.C, Rank.FIVE)
            )
        );
    }

}
