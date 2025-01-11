package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.Piece;

public interface BoardStateEventListener {

    public void onBoardStateEvent(BoardStateEvent event, Piece piece);

}
