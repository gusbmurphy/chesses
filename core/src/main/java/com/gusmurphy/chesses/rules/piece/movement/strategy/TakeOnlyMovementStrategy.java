package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.piece.movement.MustTakeMove;

import java.util.List;
import java.util.stream.Collectors;

public class TakeOnlyMovementStrategy implements MovementStrategy {

    private final MovementStrategy wrappedStrategy;

    public TakeOnlyMovementStrategy(MovementStrategy strategy) {
        wrappedStrategy = strategy;
    }

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        return wrappedStrategy
            .possibleMovesFrom(position)
            .stream()
            .map(MustTakeMove::new)
            .collect(Collectors.toList());
    }

}
