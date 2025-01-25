package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseJudge implements Judge {

    protected final List<TurnChangeListener> listeners = new ArrayList<>();

    @Override
    public void subscribeToTurnChange(TurnChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public abstract void submitMove(Piece piece, BoardCoordinates spot);

    @Override
    public abstract List<PieceMove> getPossibleMoves();
}
