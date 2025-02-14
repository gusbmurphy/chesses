package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.RequiredSafeSpaceMove;
import com.gusmurphy.chesses.rules.piece.movement.move.UnassociatedMove;

import java.util.List;
import java.util.stream.Collectors;

// TODO: Seems like there should be a generic decorator perhaps...
public class RequiredSafeSpaceStrategy extends MovementStrategyDecorator {

    private final List<Coordinates> requiredSafeSpaces;

    public RequiredSafeSpaceStrategy(List<Coordinates> requiredSafeSpaces, MovementStrategy base) {
        super(base);
        this.requiredSafeSpaces = requiredSafeSpaces;
    }

    @Override
    public List<UnassociatedMove> possibleMovesFrom(Coordinates position) {
        return super
            .possibleMovesFrom(position)
            .stream()
            .map(this::createSafeSpaceMove)
            .collect(Collectors.toList());
    }

    private RequiredSafeSpaceMove createSafeSpaceMove(UnassociatedMove move) {
        return new RequiredSafeSpaceMove(move, requiredSafeSpaces);
    }
}
