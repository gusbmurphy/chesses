package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearMovementStrategy implements MovementStrategy {

    private final List<Direction> directions;
    private final int maxDistance;

    public LinearMovementStrategy(List<Direction> directions, int maxDistance) {
        this.directions = directions;
        this.maxDistance = maxDistance;
    }

    public LinearMovementStrategy(List<Direction> directions) {
        this.directions = directions;
        this.maxDistance = 99;
    }

    @Override
    public List<PossibleMove> possibleMovesFrom(BoardCoordinates position) {
        ArrayList<PossibleMove> moves = new ArrayList<>();

        for (Direction direction : directions) {
            position.coordinatesToThe(direction).ifPresent(moveStart -> {
                PossibleMove move = new PossibleLinearMove(moveStart, direction, maxDistance - 1);
                moves.add(move);
            });
        }

        return moves;
    }

}
