package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.ArrayList;
import java.util.List;

public class MovementStrategyStub implements MovementStrategy {

    public final List<BoardCoordinates> possibleMoves = new ArrayList<>();
    public boolean isLinear = false;

    @Override
    public List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position) {
        return possibleMoves;
    }

    @Override
    public boolean isLinear() {
        return isLinear;
    }

}
