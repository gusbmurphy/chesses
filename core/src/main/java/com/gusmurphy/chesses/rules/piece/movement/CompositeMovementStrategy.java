package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.BoardStateEvent;
import com.gusmurphy.chesses.rules.board.BoardStateEventListener;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

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
    public List<Move> possibleMovesFrom(BoardCoordinates position) {
        List<Move> moves = new ArrayList<>();
        strategies.forEach(strategy -> moves.addAll(strategy.possibleMovesFrom(position)));
        return moves.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void onBoardStateEvent(BoardStateEvent event, Piece piece) {
        strategies.forEach(strategy -> {
            if (strategy instanceof PieceAwareMovementStrategy) {
                ((BoardStateEventListener) strategy).onBoardStateEvent(event, piece);
            }
        });
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
