package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.Piece;

public interface BoardStateEventListener {

    void onBoardStateEvent(BoardStateEvent event, Piece piece);

}
