package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.movement.strategy.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.NoTakeMovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.RelativeMovementStrategy;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static org.junit.jupiter.api.Assertions.*;

public class PieceTests {

    @Test
    void whenAPieceIsMovedAnyListenersAreNotified() {
        Piece piece = DefaultPieces.king(WHITE, A4);

        BoardState boardState = new BoardState();
        boardState.place(piece);

        TestPieceEventListener listener = new TestPieceEventListener();
        piece.subscribeToEvents(listener);

        piece.moveTo(D5);
        Optional<Piece> lastMovedPiece = listener.getLastMovedPiece();

        assertEquals(D5, piece.getCoordinates());
        assertEquals(piece, lastMovedPiece.get());
    }

    @Test
    void aPieceThreatensAcoordinatesOfTheOppositeColorThatItCanTake() {
        Piece piece = DefaultPieces.rook(BLACK, D4);
        new BoardState(piece);

        assertTrue(piece.threatens(WHITE, D5));
    }

    @Test
    void aPieceDoesNotThreatenAcoordinatesOfTheSameColorThatItCanTake() {
        Piece piece = DefaultPieces.rook(BLACK, D4);
        new BoardState(piece);

        assertFalse(piece.threatens(BLACK, D5));
    }

    @Test
    void aPieceDoesNotThreatenAcoordinatesItCantTake() {
        Piece piece = DefaultPieces.rook(BLACK, D4);
        new BoardState(piece);

        assertFalse(piece.threatens(WHITE, E7));
    }

    @Test
    void aPieceDoesNotThreatenAcoordinatesItCanMoveToButNotTake() {
        MovementStrategy movementStrategy = new NoTakeMovementStrategy(
            new RelativeMovementStrategy(1, 0)
        );
        Piece piece = new Piece(WHITE, movementStrategy, D3, PieceType.BISHOP);
        new BoardState(piece);

        assertFalse(piece.threatens(BLACK, E3));
    }

}
