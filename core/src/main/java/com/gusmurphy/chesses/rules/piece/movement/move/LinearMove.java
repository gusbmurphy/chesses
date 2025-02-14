package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class LinearMove implements UnassociatedMove {

    private final Coordinates from;
    private final Direction direction;
    private final int distance;

    public LinearMove(Coordinates from, Direction direction, int distance) {
        this.from = from;
        this.direction = direction;
        this.distance = distance;
    }

    @Override
    public Coordinates coordinates() {
        return from;
    }

    @Override
    public Optional<UnassociatedMove> next() {
        int remainingDistance = distance - 1;

        if (remainingDistance >= 0) {
            Optional<Coordinates> nextSpot = from.coordinatesToThe(direction);

            if (nextSpot.isPresent()) {
                return Optional.of(new LinearMove(nextSpot.get(), direction, remainingDistance));
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Piece> takes() {
        return Optional.empty();
    }

    @Override
    public boolean mustTake() {
        return false;
    }

}
