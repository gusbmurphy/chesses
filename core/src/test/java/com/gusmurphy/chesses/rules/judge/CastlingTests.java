package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.DefaultPieceFactory;
import com.gusmurphy.chesses.rules.piece.Piece;
import org.junit.jupiter.api.Test;

import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static com.gusmurphy.chesses.rules.board.coordinates.Coordinates.*;
import static org.junit.jupiter.api.Assertions.*;

public class CastlingTests {

    @Test
    public void castlingMovesBothTheWhiteKingAndLeftRook() {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece leftRook = pieceFactory.rook(WHITE, A1);
        Piece king = pieceFactory.king(WHITE);

        Judge judge = new Judge(new BoardState(leftRook, king));
        judge.submitMove(king, C1);

        assertEquals(C1, king.getCoordinates());
        assertEquals(D1, leftRook.getCoordinates());
    }

    @Test
    public void castlingMovesBothTheWhiteKingAndRightRook() {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece rightRook = pieceFactory.rook(WHITE, H1);
        Piece king = pieceFactory.king(WHITE);

        Judge judge = new Judge(new BoardState(rightRook, king));
        judge.submitMove(king, G1);

        assertEquals(G1, king.getCoordinates());
        assertEquals(F1, rightRook.getCoordinates());
    }

    @Test
    public void castlingMovesBothTheBlackKingAndLeftRook() {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece leftRook = pieceFactory.rook(BLACK, A8);
        Piece king = pieceFactory.king(BLACK);

        Judge judge = new Judge(new BoardState(leftRook, king));
        judge.submitMove(king, C8);

        assertEquals(C8, king.getCoordinates());
        assertEquals(D8, leftRook.getCoordinates());
    }

    @Test
    public void castlingMovesBothTheBlackKingAndRightRook() {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece rightRook = pieceFactory.rook(BLACK, H8);
        Piece king = pieceFactory.king(BLACK);

        Judge judge = new Judge(new BoardState(rightRook, king));
        judge.submitMove(king, G8);

        assertEquals(G8, king.getCoordinates());
        assertEquals(F8, rightRook.getCoordinates());
    }

}
