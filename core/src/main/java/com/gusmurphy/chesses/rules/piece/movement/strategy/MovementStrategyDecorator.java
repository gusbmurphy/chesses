package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.List;

public abstract class MovementStrategyDecorator implements MovementStrategy {

    protected final MovementStrategy wrappedStrategy;

    MovementStrategyDecorator(MovementStrategy strategy) {
        wrappedStrategy = strategy;
    }

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        return wrappedStrategy.possibleMovesFrom(position);
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        wrappedStrategy.onPieceEvent(event, piece);
    }

    @Override
    public void setRelevantPiece(Piece piece) {
        wrappedStrategy.setRelevantPiece(piece);
    }

}
