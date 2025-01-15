package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardStateEvent;
import com.gusmurphy.chesses.board.BoardStateEventListener;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;

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
    public List<PossibleMove> possibleMovesFrom(BoardCoordinates position) {
        List<PossibleMove> moves = new ArrayList<>();
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
