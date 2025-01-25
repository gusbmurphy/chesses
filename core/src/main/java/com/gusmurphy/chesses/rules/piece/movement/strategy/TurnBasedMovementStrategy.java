package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.Move;

import java.util.Collections;
import java.util.List;

public class TurnBasedMovementStrategy extends PieceAwareMovementStrategy {

    private int moveCount = 0;
    private final int expirationMoveCount;
    private final MovementStrategy strategy;

    public TurnBasedMovementStrategy(int expirationMoveCount, MovementStrategy strategy) {
        this.expirationMoveCount = expirationMoveCount;
        this.strategy = strategy;
    }

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        if (moveCount < expirationMoveCount) {
            return strategy.possibleMovesFrom(position);
        }

        return Collections.emptyList();
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        if (event == PieceEvent.MOVED && piece == super.relevantPiece) {
            moveCount++;
        }
    }

}
