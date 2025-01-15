package com.gusmurphy.chesses.piece.movement;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.Collections;
import java.util.List;

public class NullMovementStrategy implements MovementStrategy {

    @Override
    public List<PossibleMove> possibleMovesFrom(BoardCoordinates position) {
        return Collections.emptyList();
    }

}
