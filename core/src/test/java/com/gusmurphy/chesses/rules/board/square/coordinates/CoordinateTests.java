package com.gusmurphy.chesses.rules.board.square.coordinates;

import com.gusmurphy.chesses.rules.board.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.D4;
import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.E5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static com.gusmurphy.chesses.rules.board.Direction.*;

public class CoordinateTests {

    @ParameterizedTest
    @MethodSource("adjacentDirections")
    void directionFromOnecoordinatesToTheOtherIsCorrectlyDetermined(Coordinates coordinates, Direction expected) {
        assertEquals(D4.directionTo(coordinates), expected);
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
