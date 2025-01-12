package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.Optional;

public class PossibleLinearMove implements PossibleMove {

    private final BoardCoordinates from;
    private final Direction direction;
    private final int distance;

    public PossibleLinearMove(BoardCoordinates from, Direction direction, int distance) {
        this.from = from;
        this.direction = direction;
        this.distance = distance;
    }

    @Override
    public BoardCoordinates spot() {
        return from;
    }

    @Override
    public Optional<Direction> continuedDirection() {
        return Optional.of(direction);
    }

    @Override
    public int continuedDistance() {
        return distance;
    }
}
