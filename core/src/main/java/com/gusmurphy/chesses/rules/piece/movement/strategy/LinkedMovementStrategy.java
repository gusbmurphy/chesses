package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.LinkedMove;
import com.gusmurphy.chesses.rules.piece.movement.move.UnassociatedMove;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.List;
import java.util.stream.Collectors;

public class LinkedMovementStrategy extends MovementStrategyDecorator {

    private final Move linkedMove;

    public LinkedMovementStrategy(MovementStrategy base, Move linkedMove) {
        super(base);
        this.linkedMove = linkedMove;
    }

    @Override
    public List<UnassociatedMove> possibleMovesFrom(Coordinates position) {
        return super.possibleMovesFrom(position)
            .stream()
            .map(move -> new LinkedMove(move, linkedMove))
            .collect(Collectors.toList());
    }
}
