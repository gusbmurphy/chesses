package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceType;
import org.junit.jupiter.api.Test;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PawnTransformTests {

    @Test
    public void whenAPawnReachesTheOtherSideOfTheBoardAListenerCanDecideAPieceToTransformTo() {
        Piece pawn = DefaultPieces.pawn(WHITE, H7);
        BoardState board = new BoardState(pawn);
        Judge judge = new Judge(board);

        TestPawnTransformListener listener = new TestPawnTransformListener();
        listener.respondWith(PieceType.BISHOP);
        judge.subscribeToPawnTransform(listener);

        judge.submitMove(pawn, H8);

        assertEquals(PieceType.BISHOP, pawn.type());
    }

}
