package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.BoardStateEventManager;
import com.gusmurphy.chesses.rules.PlayerColor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.gusmurphy.chesses.rules.board.BoardStateEvent.PIECE_MOVED;
import static com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceTests {

    @Test
    void whenAPieceIsMovedAnyListenersAreNotified() {
        Piece piece = DefaultPieces.king(PlayerColor.WHITE, A4);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        BoardStateEventManager eventManager = new BoardStateEventManager(boardState);
        TestPieceEventListener listener = new TestPieceEventListener();
        eventManager.subscribe(listener, PIECE_MOVED);

        piece.moveTo(D5);
        Optional<Piece> lastMovedPiece = listener.getLastMovedPiece();

        assertEquals(D5, piece.getCoordinates());
        assertEquals(piece, lastMovedPiece.get());
    }

}
