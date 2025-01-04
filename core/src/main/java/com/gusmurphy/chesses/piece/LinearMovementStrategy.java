package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.Direction;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LinearMovementStrategy implements MovementStrategy {

    private final List<Direction> directions;

    public LinearMovementStrategy(List<Direction> directions, int maxDistance) {
        this.directions = directions;
    }

    @Override
    public List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position) {
        Direction direction = directions.getFirst();
        Optional<BoardCoordinates> move = position.coordinatesToThe(direction);
        return move.map(Collections::singletonList).orElse(Collections.emptyList());
    }

}
