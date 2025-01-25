package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.List;
import java.util.stream.Collectors;

public class TakeOnlyMovementStrategy extends PieceAwareMovementStrategy {

    private final MovementStrategy wrappedStrategy;

    public TakeOnlyMovementStrategy(MovementStrategy strategy) {
        wrappedStrategy = strategy;
    }

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        return wrappedStrategy
            .possibleMovesFrom(position)
            .stream()
            .map(MustTakeMove::new)
            .collect(Collectors.toList());
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
    }

}
