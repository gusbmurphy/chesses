package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.move.NoTakeMove;

import java.util.List;
import java.util.stream.Collectors;

public class NoTakeMovementStrategy extends MovementStrategyDecorator {

    public NoTakeMovementStrategy(MovementStrategy strategy) {
        super(strategy);
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
