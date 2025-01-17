package com.gusmurphy.chesses.rules.board.coordinates;

import com.gusmurphy.chesses.rules.board.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates.D4;
import static com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates.E5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates.*;
import static com.gusmurphy.chesses.rules.board.Direction.*;

public class BoardCoordinateTests {

    @ParameterizedTest
    @MethodSource("adjacentDirections")
    void directionFromOneSpotToTheOtherIsCorrectlyDetermined(BoardCoordinates spot, Direction expected) {
        assertEquals(D4.directionTo(spot), expected);
    }

    private static Stream<Arguments> adjacentDirections() {
        return Stream.of(
            Arguments.of(D5, N),
            Arguments.of(E5, NE),
            Arguments.of(E4, E),
            Arguments.of(E3, SE),
            Arguments.of(D3, S),
            Arguments.of(C3, SW),
            Arguments.of(C4, W),
            Arguments.of(C5, NW)
        );
    }

}
