package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinatesXyAdapter;

import java.util.ArrayList;
import java.util.List;

public class RelativeStrategy implements MovementStrategy {

    private final List<MovementVector> movementVectors = new ArrayList<>();

    public RelativeStrategy(int x, int y) {
        movementVectors.add(new MovementVector(x, y));
    }

    public RelativeStrategy(RelativeStrategy... strategies) {
        for (RelativeStrategy strategy : strategies) {
            movementVectors.addAll(strategy.movementVectors);
        }
    }

    @Override
    public List<PossibleMove> possibleMovesFrom(BoardCoordinates position) {
        BoardCoordinatesXyAdapter xyAdapter = new BoardCoordinatesXyAdapter(position);

        List<PossibleMove> moves = new ArrayList<>();
        for (MovementVector vector : movementVectors) {
            BoardCoordinates moveSpot = new BoardCoordinatesXyAdapter(xyAdapter.x() + vector.x, xyAdapter.y() + vector.y).coordinates();
            moves.add(new PossibleStaticMove(moveSpot));
        }
        return moves;
    }

    static class MovementVector {
        public int x;
        public int y;

        public MovementVector(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
