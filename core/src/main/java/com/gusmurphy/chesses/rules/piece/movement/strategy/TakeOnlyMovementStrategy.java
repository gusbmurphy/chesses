package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.move.MustTakeMove;

import java.util.List;
import java.util.stream.Collectors;

public class TakeOnlyMovementStrategy extends MovementStrategyDecorator {

    public TakeOnlyMovementStrategy(MovementStrategy strategy) {
        super(strategy);
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
