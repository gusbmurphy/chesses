package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.Collections;
import java.util.List;

public class NullMovementStrategy implements MovementStrategy {

    @Override
    public List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position) {
        return Collections.emptyList();
    }

    @Override
    public boolean isLinear() {
        return false;
    }

}
