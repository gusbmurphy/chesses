package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.board.square.coordinates.CoordinatesXyAdapter;
import com.gusmurphy.chesses.rules.piece.movement.move.UnassociatedMove;
import com.gusmurphy.chesses.rules.piece.movement.move.StaticMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RelativeMovementStrategy implements MovementStrategy {

    private final List<MovementVector> movementVectors = new ArrayList<>();

    public RelativeMovementStrategy(int x, int y) {
        movementVectors.add(new MovementVector(x, y));
    }

    public RelativeMovementStrategy(RelativeMovementStrategy... strategies) {
        for (RelativeMovementStrategy strategy : strategies) {
            movementVectors.addAll(strategy.movementVectors);
        }
    }

    @Override
    public List<UnassociatedMove> possibleMovesFrom(Coordinates position) {
        List<UnassociatedMove> moves = new ArrayList<>();
        for (MovementVector vector : movementVectors) {
            Optional<Coordinates> movecoordinates = getPositionAtVectorFromOther(vector, position);
            movecoordinates.ifPresent(coordinates -> moves.add(new StaticMove(coordinates)));
        }
        return moves;
    }

    private static Optional<Coordinates> getPositionAtVectorFromOther(MovementVector vector, Coordinates position) {
        CoordinatesXyAdapter adapter = new CoordinatesXyAdapter(position);

        try {
            return Optional.of(new CoordinatesXyAdapter(
                adapter.x() + vector.x,
                adapter.y() + vector.y
            ).coordinates());
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    static class MovementVector {
        public final int x;
        public final int y;

        public MovementVector(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
