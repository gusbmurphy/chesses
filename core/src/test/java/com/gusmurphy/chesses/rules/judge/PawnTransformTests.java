package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PawnTransformTests {

    @ParameterizedTest
    @MethodSource("pawnReachingOtherSideMoves")
    public void whenAPawnReachesTheOtherSideOfTheBoardAListenerCanDecideAPieceToTransformTo(
        PlayerColor color, Coordinates start, Coordinates move
    ) {
        Piece pawn = DefaultPieces.pawn(color, start);
        BoardState board = new BoardState(pawn);
        Judge judge = new Judge(board);

        TestPawnTransformListener listener = new TestPawnTransformListener();
        listener.respondWith(PieceType.BISHOP);
        judge.subscribeToPawnTransform(listener);

        judge.submitMove(pawn, move);

        assertEquals(PieceType.BISHOP, pawn.type());
    }

    private static Stream<Arguments> pawnReachingOtherSideMoves() {
        return Stream.of(
            Arguments.of(WHITE, H7, H8),
            Arguments.of(BLACK, H2, H1)
        );
    }

    @Test
    public void ifAPawnHappensToBeOnItsOwnSideWhenAMoveHappensItDoesNotTransform() {
        // How did these pawns end up here? I do not know...
        Piece whitePawn = DefaultPieces.pawn(WHITE, G1);
        Piece blackPawn = DefaultPieces.pawn(BLACK, H8);
        Piece rook = DefaultPieces.rook(WHITE, E1);
        BoardState board = new BoardState(whitePawn, rook);
        Judge judge = new Judge(board);

        TestPawnTransformListener listener = new TestPawnTransformListener();
        listener.respondWith(PieceType.BISHOP);
        judge.subscribeToPawnTransform(listener);

        judge.submitMove(rook, E3);

        assertEquals(PieceType.PAWN, whitePawn.type());
        assertEquals(PieceType.PAWN, blackPawn.type());
    }

}
