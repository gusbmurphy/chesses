package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.LinkedMove;
import com.gusmurphy.chesses.rules.piece.movement.move.UnassociatedMove;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;

import java.util.List;
import java.util.stream.Collectors;

public class LinkedMovementStrategy extends MovementStrategyDecorator {

    private final PieceMove linkedMove;

    public LinkedMovementStrategy(MovementStrategy base, PieceMove linkedMove) {
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
