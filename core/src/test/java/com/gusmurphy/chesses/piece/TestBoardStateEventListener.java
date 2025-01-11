package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardStateEvent;
import com.gusmurphy.chesses.board.BoardStateEventListener;

import java.util.Optional;

public class TestBoardStateEventListener implements BoardStateEventListener {

    private Piece lastMovedPiece;

    public Optional<Piece> getLastMovedPiece() {
        return Optional.ofNullable(lastMovedPiece);
    }

    @Override
    public void onBoardStateEvent(BoardStateEvent event, Piece piece) {
        if (event == BoardStateEvent.PIECE_MOVED) {
            lastMovedPiece = piece;
        }
    }

}
