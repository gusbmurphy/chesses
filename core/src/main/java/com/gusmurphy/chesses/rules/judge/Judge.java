package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;

import java.util.ArrayList;
import java.util.List;

public abstract class Judge {

    protected final List<TurnChangeListener> listeners = new ArrayList<>();

    public abstract void submitMove(Piece piece, BoardCoordinates spot);
    public abstract List<PieceMove> getPossibleMoves();

    public void subscribeToTurnChange(TurnChangeListener listener) {
        listeners.add(listener);
    }

}
