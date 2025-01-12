package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.Piece;

public interface BoardStateEventListener {

    void onBoardStateEvent(BoardStateEvent event, Piece piece);

}
