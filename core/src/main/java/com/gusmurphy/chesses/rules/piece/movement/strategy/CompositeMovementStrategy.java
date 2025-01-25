package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.*;
import java.util.stream.Collectors;

public class CompositeMovementStrategy extends PieceAwareMovementStrategy {

    private final List<MovementStrategy> strategies;

    public CompositeMovementStrategy(
        MovementStrategy... strategies
    ) {
        this.strategies = Arrays.asList(strategies);
    }

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        List<Move> moves = new ArrayList<>();
        strategies.forEach(strategy -> moves.addAll(strategy.possibleMovesFrom(position)));
        return moves.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        strategies.forEach(strategy -> strategy.onPieceEvent(event, piece));
    }

    @Override
    public void setRelevantPiece(Piece piece) {
        super.setRelevantPiece(piece);
        strategies.forEach(strategy -> {
            if (strategy instanceof PieceAwareMovementStrategy) {
                ((PieceAwareMovementStrategy) strategy).setRelevantPiece(piece);
            }
        });
    }

}
