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
    @MethodSource("provideDirectionsAndDistanceForLinear")
    void aLinearMovementStrategyLimitsMovementToItsDirectionAndDistance(
        List<Direction> directions, int distance, List<BoardCoordinates> expected
    ) {
        MovementStrategy linear = new LinearMovementStrategy(directions, distance);
        List<BoardCoordinates> possibleMoves = linear.possibleMovesFrom(new BoardCoordinates(File.D, Rank.FOUR));
        Assertions.assertEquals(expected, possibleMoves);
    }

    private static Stream<Arguments> provideDirectionsAndDistanceForLinear() {
        return Stream.of(
            Arguments.of(
                Collections.singletonList(Direction.N),
                1,
                Collections.singletonList(new BoardCoordinates(File.D, Rank.FIVE))
            )
        );
    }

}
