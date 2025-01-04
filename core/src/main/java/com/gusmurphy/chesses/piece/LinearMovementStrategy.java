package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearMovementStrategy implements MovementStrategy {

    private final List<Direction> directions;
    private final int maxDistance;

    public LinearMovementStrategy(List<Direction> directions, int maxDistance) {
        this.directions = directions;
        this.maxDistance = maxDistance;
    }

    @Override
    public List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();

        for (Direction direction : directions) {
            moves.addAll(getMovesFromPositionInDirection(position, direction));
        }

        return moves;
    }

    private ArrayList<BoardCoordinates> getMovesFromPositionInDirection(BoardCoordinates position, Direction direction) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        Optional<BoardCoordinates> currentSpot = Optional.of(position);

        for (int distance = 0; distance < maxDistance && currentSpot.isPresent(); distance++) {
            Optional<BoardCoordinates> move = currentSpot.get().coordinatesToThe(direction);
            move.ifPresent(moves::add);
            currentSpot = move;
        }

        return moves;
    }

}
