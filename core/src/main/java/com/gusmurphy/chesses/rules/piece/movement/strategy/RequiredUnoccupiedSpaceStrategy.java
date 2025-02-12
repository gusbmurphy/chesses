package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.UnassociatedMove;
import com.gusmurphy.chesses.rules.piece.movement.move.RequiredUnoccupiedSpaceMove;

import java.util.List;
import java.util.stream.Collectors;

public class RequiredUnoccupiedSpaceStrategy extends MovementStrategyDecorator {

    private final List<Coordinates> requiredUnoccupiedSpaces;

    public RequiredUnoccupiedSpaceStrategy(List<Coordinates> requiredUnoccupiedSpaces, MovementStrategy base) {
        super(base);
        this.requiredUnoccupiedSpaces = requiredUnoccupiedSpaces;
    }

    @Override
    public List<UnassociatedMove> possibleMovesFrom(Coordinates position) {
        List<UnassociatedMove> moves = super.possibleMovesFrom(position);
        return moves
            .stream()
            .map(this::createUnoccupiedSpaceMove)
            .collect(Collectors.toList());
    }

    private RequiredUnoccupiedSpaceMove createUnoccupiedSpaceMove(UnassociatedMove move) {
        return new RequiredUnoccupiedSpaceMove(move, requiredUnoccupiedSpaces);
    }

}
