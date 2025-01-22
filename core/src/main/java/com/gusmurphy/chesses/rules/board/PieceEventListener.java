package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.Piece;

public interface PieceEventListener {
    void onBoardStateEvent(PieceEvent event, Piece piece);
}
