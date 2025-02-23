package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.List;

public abstract class JudgeDecorator extends Judge {

    protected final Judge wrappedJudge;

    JudgeDecorator(Judge judge) {
        super(judge.boardState);
        wrappedJudge = judge;
    }

    @Override
    public void submitMove(Piece piece, Coordinates coordinates) {
        wrappedJudge.submitMove(piece, coordinates);
    }

    @Override
    public List<Move> getPossibleMoves() {
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

    @Override
    public void subscribeToPawnTransform(PawnTransformListener listener) {
        wrappedJudge.subscribeToPawnTransform(listener);
    }

    @Override
    protected void notifyGameOverListeners(GameOverEvent event) {
        wrappedJudge.notifyGameOverListeners(event);
    }

    @Override
    protected void notifyTurnChangeListeners(PlayerColor newTurnColor) {
        wrappedJudge.notifyTurnChangeListeners(newTurnColor);
    }
}
