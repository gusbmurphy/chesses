package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.Collections;
import java.util.List;

public class TurnBasedMovementStrategy extends MovementStrategyDecorator {

    private Piece relevantPiece;
    private int moveCount = 0;
    private final int expirationMoveCount;

    public TurnBasedMovementStrategy(int expirationMoveCount, MovementStrategy strategy) {
        super(strategy);
        this.expirationMoveCount = expirationMoveCount;
    }

    public TurnBasedMovementStrategy(int expirationMoveCount, MovementStrategy strategy, Piece relevantPiece) {
        super(strategy);
        this.expirationMoveCount = expirationMoveCount;
        this.relevantPiece = relevantPiece;
    }

    @Override
    public List<Move> possibleMovesFrom(Coordinates position) {
        if (moveCount < expirationMoveCount) {
            return super.possibleMovesFrom(position);
        }

        return Collections.emptyList();
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        if (event == PieceEvent.MOVED && piece == relevantPiece) {
            moveCount++;
        }
        super.onPieceEvent(event, piece);
    }

    @Override
    public void setRelevantPiece(Piece piece) {
        if (relevantPiece == null) {
            relevantPiece = piece;
        }
    }

}
