package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.Piece;

public interface PieceEventListener {

    default void onPieceEvent(PieceEvent event, Piece piece) {
    }

}
