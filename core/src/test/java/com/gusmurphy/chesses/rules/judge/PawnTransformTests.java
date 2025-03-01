package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.PieceFactory;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceType;
import com.gusmurphy.chesses.rules.piece.TestPieceEventListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PawnTransformTests {

    private final PieceFactory pieceFactory = new PieceFactory();

    @ParameterizedTest
    @MethodSource("pawnReachingOtherSideMoves")
    public void whenAPawnReachesTheOtherSideOfTheBoardAListenerCanDecideAPieceToTransformTo(
        PlayerColor color, Coordinates start, Coordinates move
    ) {
        Piece pawn = pieceFactory.pawn(color, start);
        BoardState board = new BoardState(pawn);
        Judge judge = new Judge(board);

        TestPawnTransformRequestListener listener = new TestPawnTransformRequestListener();
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
    public void onceAPawnReachesTheOtherSideItCanMoveLikeTheNewPiece() {
        Piece pawn = pieceFactory.pawn(WHITE, H7);
        BoardState board = new BoardState(pawn);
        Judge judge = new Judge(board);

        TestPawnTransformRequestListener listener = new TestPawnTransformRequestListener();
        listener.respondWith(PieceType.BISHOP);
        judge.subscribeToPawnTransform(listener);

        judge.submitMove(pawn, H8);
        judge.submitMove(pawn, A1); // It can now move like a bishop...

        assertEquals(A1, pawn.getCoordinates());
    }

    @Test
    public void whenAPawnIsTransformedListenersAreNotified() {
        Piece pawn = pieceFactory.pawn(WHITE, H7);
        BoardState board = new BoardState(pawn);
        Judge judge = new Judge(board);

        TestPawnTransformRequestListener listener = new TestPawnTransformRequestListener();
        listener.respondWith(PieceType.BISHOP);
        judge.subscribeToPawnTransform(listener);

        TestPieceEventListener pieceEventListener = new TestPieceEventListener();
        pawn.subscribeToEvents(pieceEventListener);

        judge.submitMove(pawn, H8);
        judge.submitMove(pawn, A1); // It can now move like a bishop...

        assertEquals(pawn, pieceEventListener.getLastPieceTransformed().get());
    }

    @Test
    public void ifAPawnHappensToBeOnItsOwnSideWhenAMoveHappensItDoesNotTransform() {
        // How did these pawns end up here? I do not know...
        Piece whitePawn = pieceFactory.pawn(WHITE, G1);
        Piece blackPawn = pieceFactory.pawn(BLACK, H8);
        Piece rook = pieceFactory.rook(WHITE, E1);
        BoardState board = new BoardState(whitePawn, rook);
        Judge judge = new Judge(board);

        TestPawnTransformRequestListener listener = new TestPawnTransformRequestListener();
        listener.respondWith(PieceType.BISHOP);
        judge.subscribeToPawnTransform(listener);

        judge.submitMove(rook, E3);

        assertEquals(PieceType.PAWN, whitePawn.type());
        assertEquals(PieceType.PAWN, blackPawn.type());
    }

    @Test
    public void noMovesCanBeMadeWhileWaitingForTheTransform() {
        Piece pawn = pieceFactory.pawn(WHITE, H7);
        Piece bishop = pieceFactory.bishop(BLACK, B3);
        BoardState board = new BoardState(pawn, bishop);
        Judge judge = new Judge(board);

        TestPawnTransformRequestListener listener = new TestPawnTransformRequestListener();
        listener.dontRespond();
        judge.subscribeToPawnTransform(listener);

        judge.submitMove(pawn, H8);
        judge.submitMove(bishop, C4);

        assertEquals(B3, bishop.getCoordinates());
    }

}
