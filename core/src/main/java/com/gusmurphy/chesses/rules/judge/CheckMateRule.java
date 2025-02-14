package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.List;

public class CheckMateRule extends JudgeDecorator {

    public CheckMateRule(Judge judge) {
        super(judge);
    }

    @Override
    public void submitMove(Piece piece, Coordinates coordinates) {
        super.submitMove(piece, coordinates);

        List<Move> movesNow = getPossibleMoves();
        if (movesNow.isEmpty()) {
            notifyGameOverListeners(new GameOverEvent(GameOverEventType.CHECKMATE, piece.color()));
        }
    }

}
