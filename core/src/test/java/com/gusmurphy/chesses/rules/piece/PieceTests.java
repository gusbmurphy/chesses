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

    private final PieceFactory pieceFactory = new PieceFactory();

    @Test
    void whenAPieceIsMovedAnyListenersAreNotified() {
        Piece piece = pieceFactory.king(WHITE, A4);

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
    void aPieceThreatensCoordinatesOfTheOppositeColorThatItCanTake() {
        Piece piece = pieceFactory.rook(BLACK, D4);
        new BoardState(piece);

        assertTrue(piece.threatens(WHITE, D5));
    }

    @Test
    void aPieceDoesNotThreatensCoordinatesOfTheSameColorThatItCanTake() {
        Piece piece = pieceFactory.rook(BLACK, D4);
        new BoardState(piece);

        assertFalse(piece.threatens(BLACK, D5));
    }

    @Test
    void aPieceDoesNotThreatenCoordinatesItCantTake() {
        Piece piece = pieceFactory.rook(BLACK, D4);
        new BoardState(piece);

        assertFalse(piece.threatens(WHITE, E7));
    }

    @Test
    void aPieceDoesNotThreatenCoordinatesItCanMoveToButNotTake() {
        MovementStrategy movementStrategy = new NoTakeMovementStrategy(
            new RelativeMovementStrategy(1, 0)
        );
        Piece piece = new Piece(WHITE, movementStrategy, D3, PieceType.BISHOP);
        new BoardState(piece);

        assertFalse(piece.threatens(BLACK, E3));
    }

    @Test
    void aPieceDoesNotThreatenCoordinatesBlockedByAnotherPiece() {
        Piece piece = pieceFactory.rook(BLACK, D4);
        Piece blocker = pieceFactory.rook(BLACK, D5);
        new BoardState(piece, blocker);

        assertFalse(piece.threatens(WHITE, D6));
    }

    @Test
    void whenCopiedTheTwoPiecesShareBoardIdentification() {
        Piece original = pieceFactory.pawn(BLACK, D2);
        Piece someOtherPiece = pieceFactory.pawn(BLACK, D3);
        new BoardState(original, someOtherPiece);
        Piece copy = new Piece(original);

        assertTrue(original.sameBoardIdAs(copy));
        assertFalse(original.sameBoardIdAs(someOtherPiece));
    }

}
