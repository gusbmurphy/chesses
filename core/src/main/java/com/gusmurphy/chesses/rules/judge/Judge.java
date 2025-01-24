package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;

import java.util.List;

public interface Judge {
    void submitMove(Piece piece, BoardCoordinates spot);
    void subscribeToTurnChange(TurnChangeListener listener);
    List<PieceMove> getPossibleMoves();
}
