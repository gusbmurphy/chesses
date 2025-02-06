package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;
import org.junit.jupiter.api.Test;

import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static com.gusmurphy.chesses.rules.board.coordinates.Coordinates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EnPassantTests {

    @Test
    public void onePawnCanTakeAnotherEnPassant() {
        Piece whitePawn = DefaultPieces.pawn(WHITE, F5);
        Piece blackPawn = DefaultPieces.pawn(BLACK, E7);

        BoardState boardState = new BoardState(whitePawn, blackPawn);
        Judge judge = new Judge(boardState);

        judge.submitMove(blackPawn, E5);
        judge.submitMove(whitePawn, E6);

        assertEquals(E6, whitePawn.getCoordinates());
        assertFalse(boardState.getStateAt(E7).occupyingPiece().isPresent());
    }

}
