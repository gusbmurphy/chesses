package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.BoardStateEventManager;
import com.gusmurphy.chesses.player.PlayerColor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.gusmurphy.chesses.board.BoardStateEvent.PIECE_MOVED;
import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceTests {

    @Test
    void whenAPieceIsMovedAnyListenersAreNotified() {
        Piece piece = new Piece(DefaultPieces.king(PlayerColor.WHITE), A4);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        BoardStateEventManager eventManager = new BoardStateEventManager(boardState);
        TestBoardStateEventListener listener = new TestBoardStateEventListener();
        eventManager.subscribe(listener, PIECE_MOVED);

        piece.moveTo(D5);
        Optional<Piece> lastMovedPiece = listener.getLastMovedPiece();

        assertEquals(D5, piece.getCoordinates());
        assertEquals(piece, lastMovedPiece.get());
    }

}
