package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;

import java.util.List;

public abstract class JudgeDecorator extends Judge {

    protected final Judge wrappedJudge;

    JudgeDecorator(Judge judge) {
        super(judge.boardState);
        wrappedJudge = judge;
    }

    @Override
    public void submitMove(Piece piece, Coordinates spot) {
        wrappedJudge.submitMove(piece, spot);
    }

    @Override
    public List<PieceMove> getPossibleMoves() {
        return wrappedJudge.getPossibleMoves();
    }

    @Override
    public void subscribeToTurnChange(TurnChangeListener listener) {
        wrappedJudge.subscribeToTurnChange(listener);
    }

    @Override
    public void subscribeToGameOver(GameOverListener listener) {
        wrappedJudge.subscribeToGameOver(listener);
    }

}
