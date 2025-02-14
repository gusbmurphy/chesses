package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.UnassociatedMove;

import java.util.*;
import java.util.stream.Collectors;

public class CompositeMovementStrategy implements MovementStrategy {

    private final List<MovementStrategy> strategies;

    public CompositeMovementStrategy(
        MovementStrategy... strategies
    ) {
        this.strategies = Arrays.asList(strategies);
    }

    @Override
    public List<UnassociatedMove> possibleMovesFrom(Coordinates position) {
        List<UnassociatedMove> moves = new ArrayList<>();
        strategies.forEach(strategy -> moves.addAll(strategy.possibleMovesFrom(position)));
        return moves.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        strategies.forEach(strategy -> strategy.onPieceEvent(event, piece));
    }

    @Override
    public void setRelevantPiece(Piece piece) {
        strategies.forEach(strategy -> strategy.setRelevantPiece(piece));
    }

}
