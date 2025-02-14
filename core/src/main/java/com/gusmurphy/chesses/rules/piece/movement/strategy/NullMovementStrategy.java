package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.UnassociatedMove;

import java.util.Collections;
import java.util.List;

public class NullMovementStrategy implements MovementStrategy {

    @Override
    public List<UnassociatedMove> possibleMovesFrom(Coordinates position) {
        return Collections.emptyList();
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
    }

}
