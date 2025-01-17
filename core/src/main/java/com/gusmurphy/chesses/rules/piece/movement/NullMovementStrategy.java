package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;

import java.util.Collections;
import java.util.List;

public class NullMovementStrategy implements MovementStrategy {

    @Override
    public List<Move> possibleMovesFrom(BoardCoordinates position) {
        return Collections.emptyList();
    }

}
