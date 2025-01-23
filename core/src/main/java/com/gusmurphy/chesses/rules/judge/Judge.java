package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.Move;

import java.util.List;

public interface Judge {
    List<Move> possibleMovesFor(Piece piece);

    void submitMove(Piece piece, BoardCoordinates spot);
}
