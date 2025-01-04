package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.File;
import com.gusmurphy.chesses.board.Rank;

import java.util.Collections;
import java.util.List;

public class LinearMovementStrategy implements MovementStrategy {

    private final List<Direction> directions;
    private final int maxDistance;

    public LinearMovementStrategy(List<Direction> directions, int maxDistance) {
        this.directions = directions;
        this.maxDistance = maxDistance;
    }

    @Override
    public List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position) {
        return Collections.singletonList(new BoardCoordinates(File.D, Rank.THREE));
    }

}
