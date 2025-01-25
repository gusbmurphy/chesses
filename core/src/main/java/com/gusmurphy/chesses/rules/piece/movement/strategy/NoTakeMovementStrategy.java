package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.piece.movement.NoTakeMove;

import java.util.List;
import java.util.stream.Collectors;

public class NoTakeMovementStrategy implements MovementStrategy {

    private final MovementStrategy wrappedStrategy;

    public NoTakeMovementStrategy(MovementStrategy strategy) {
        wrappedStrategy = strategy;
    }

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        return wrappedStrategy
            .possibleMovesFrom(position)
            .stream()
            .map(NoTakeMove::new)
            .collect(Collectors.toList());
    }
}
