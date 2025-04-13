package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceFactory;
import org.junit.jupiter.api.Test;

import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static org.junit.jupiter.api.Assertions.*;

public class EnPassantTests {

    private final PieceFactory pieceFactory = new PieceFactory();

    @Test
    public void aWhitePawnCanTakeEnPassantOnTheLeft() {
        Piece whitePawn = pieceFactory.pawn(WHITE, F5);
        Piece blackPawn = pieceFactory.pawn(BLACK, E7);

        BoardState boardState = new BoardState(whitePawn, blackPawn);
        Judge judge = new Judge(boardState);

        judge.submitMove(blackPawn, E5);
        judge.submitMove(whitePawn, E6);

        assertEquals(E6, whitePawn.getCoordinates());
        assertFalse(boardState.getStateAt(E5).occupyingPiece().isPresent());
    }

    @Test
    public void aWhitePawnCanTakeEnPassantOnTheRight() {
        Piece whitePawn = pieceFactory.pawn(WHITE, F5);
        Piece blackPawn = pieceFactory.pawn(BLACK, G7);

        BoardState boardState = new BoardState(whitePawn, blackPawn);
        Judge judge = new Judge(boardState);

        judge.submitMove(blackPawn, G5);
        judge.submitMove(whitePawn, G6);

        assertEquals(G6, whitePawn.getCoordinates());
        assertFalse(boardState.getStateAt(G5).occupyingPiece().isPresent());
    }

    @Test
    public void aBlackPawnCanTakeEnPassantOnTheLeft() {
        Piece whitePawn = pieceFactory.pawn(WHITE, C2);
        Piece blackPawn = pieceFactory.pawn(BLACK, D4);

        BoardState boardState = new BoardState(whitePawn, blackPawn);
        Judge judge = new Judge(boardState);

        judge.submitMove(whitePawn, C4);
        judge.submitMove(blackPawn, C3);

        assertEquals(C3, blackPawn.getCoordinates());
        assertFalse(boardState.getStateAt(C4).occupyingPiece().isPresent());
    }

    @Test
    public void aBlackPawnCanTakeEnPassantOnTheRight() {
        Piece whitePawn = pieceFactory.pawn(WHITE, E2);
        Piece blackPawn = pieceFactory.pawn(BLACK, D4);

        BoardState boardState = new BoardState(whitePawn, blackPawn);
        Judge judge = new Judge(boardState);

        judge.submitMove(whitePawn, E4);
        judge.submitMove(blackPawn, E3);

        assertEquals(E3, blackPawn.getCoordinates());
        assertFalse(boardState.getStateAt(E4).occupyingPiece().isPresent());
    }

    @Test
    public void theEnPassantMoveCannotBeTakenOnATurnBeyondTheOneImmediatelyFollowing() {
        Piece whitePawn = pieceFactory.pawn(WHITE, E2);
        Piece blackPawn = pieceFactory.pawn(BLACK, D4);
        Piece whiteBishop = pieceFactory.bishop(WHITE, H6);
        Piece blackBishop = pieceFactory.bishop(BLACK, H4);

        BoardState boardState = new BoardState(whitePawn, blackPawn, whiteBishop, blackBishop);
        Judge judge = new PlayerTurnRule(new Judge(boardState), WHITE);

        judge.submitMove(whitePawn, E4);
        judge.submitMove(blackBishop, G3);
        judge.submitMove(whiteBishop, F8);

        assertThrows(IllegalMoveException.class, () -> {
            judge.submitMove(blackPawn, E3);
        });

        // The black pawn should not have moved...
        assertEquals(D4, blackPawn.getCoordinates());
    }

}
