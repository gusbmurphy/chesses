package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.Move;

import java.util.Collections;
import java.util.List;

public class NullMovementStrategy implements MovementStrategy {

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        return Collections.emptyList();
    }

}
