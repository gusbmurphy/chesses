package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardStateEventManager;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.movement.Move;

import java.util.List;

public interface Piece {
    List<Move> currentPossibleMoves();

    BoardCoordinates getCoordinates();

    PlayerColor color();

    PieceType type();

    void moveTo(BoardCoordinates coordinates);

    void take();

    void setEventManager(BoardStateEventManager manager);
}
