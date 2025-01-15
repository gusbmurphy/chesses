package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardStateEvent;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;

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
    public List<PossibleMove> possibleMovesFrom(BoardCoordinates position) {
        if (moveCount < expirationMoveCount) {
            return strategy.possibleMovesFrom(position);
        }

        return Collections.emptyList();
    }

    @Override
    public void onBoardStateEvent(BoardStateEvent event, Piece piece) {
        if (event == BoardStateEvent.PIECE_MOVED && piece == super.relevantPiece) {
            moveCount++;
        }
    }

}
