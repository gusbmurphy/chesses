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
    public void castlingMovesBothTheKingAndRelevantRook() {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece leftRook = pieceFactory.rook(WHITE, A1);
        Piece king = pieceFactory.king(WHITE, E1);

        Judge judge = new Judge(new BoardState(leftRook, king));
        judge.submitMove(king, C1);

        assertEquals(C1, king.getCoordinates());
        assertEquals(D1, leftRook.getCoordinates());
    }

}
