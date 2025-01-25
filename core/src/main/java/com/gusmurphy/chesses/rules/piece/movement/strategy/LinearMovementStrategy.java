package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.piece.movement.move.LinearMove;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public LinearMovementStrategy(Direction... directions) {
        this.directions = Arrays.asList(directions);
        this.maxDistance = 99;
    }

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        ArrayList<Move> moves = new ArrayList<>();

        for (Direction direction : directions) {
            position.coordinatesToThe(direction).ifPresent(moveStart -> {
                Move move = new LinearMove(moveStart, direction, maxDistance - 1);
                moves.add(move);
            });
        }

        return moves;
    }

}
