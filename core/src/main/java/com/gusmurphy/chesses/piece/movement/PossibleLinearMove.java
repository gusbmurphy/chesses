package com.gusmurphy.chesses.piece.movement;

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
    public Optional<PossibleMove> next() {
        int remainingDistance = distance - 1;

        if (remainingDistance >= 0) {
            Optional<BoardCoordinates> nextSpot = from.coordinatesToThe(direction);

            if (nextSpot.isPresent()) {
                return Optional.of(new PossibleLinearMove(nextSpot.get(), direction, remainingDistance));
            }
        }

        return Optional.empty();
    }

}
