package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

public class SquareDeactivationRule extends JudgeDecorator {
    SquareDeactivationRule(Judge judge) {
        super(judge);
    }

    @Override
    public void submitMove(Piece piece, Coordinates coordinates) {
        if (piece.color() == PlayerColor.WHITE) {
            super.submitMove(piece, coordinates);
        } else {
            throw new IllegalMoveException(piece, coordinates);
        }
    }
}
