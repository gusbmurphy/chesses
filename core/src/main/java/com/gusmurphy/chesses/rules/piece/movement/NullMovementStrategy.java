package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;

import java.util.Collections;
import java.util.List;

public class NullMovementStrategy implements MovementStrategy {

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        return Collections.emptyList();
    }

}
