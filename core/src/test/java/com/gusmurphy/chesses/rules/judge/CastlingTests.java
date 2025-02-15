package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.DefaultPieceFactory;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static org.junit.jupiter.api.Assertions.*;

public class CastlingTests {

    @ParameterizedTest
    @MethodSource("castlingTestCases")
    public void castlingMovesBothTheKingAndRook(
        PlayerColor color, Coordinates rookStartingPosition, Coordinates kingMove, Coordinates expectedRookMove
    ) {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece rightRook = pieceFactory.rook(color, rookStartingPosition);
        Piece king = pieceFactory.king(color);

        Judge judge = new Judge(new BoardState(rightRook, king));
        judge.submitMove(king, kingMove);

        assertEquals(kingMove, king.getCoordinates());
        assertEquals(expectedRookMove, rightRook.getCoordinates());
    }

    @Test
    public void castlingCannotHappenIfTheKingHasMoved() {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece leftRook = pieceFactory.rook(WHITE, A1);
        Piece king = pieceFactory.king(WHITE);

        Judge judge = new Judge(new BoardState(leftRook, king));
        judge.submitMove(king, E2);
        judge.submitMove(king, C1);

        assertEquals(E2, king.getCoordinates());
        assertEquals(A1, leftRook.getCoordinates());
    }

    @Test
    public void castlingCannotHappenIfTheRookHasMoved() {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece leftRook = pieceFactory.rook(WHITE, A1);
        Piece king = pieceFactory.king(WHITE);

        Judge judge = new Judge(new BoardState(leftRook, king));
        judge.submitMove(leftRook, A2);
        judge.submitMove(king, C1);

        assertEquals(E1, king.getCoordinates());
        assertEquals(A2, leftRook.getCoordinates());
    }

    @ParameterizedTest
    @MethodSource("blockedCastlingTestCases")
    public void castlingCannotHappenIfThereArePiecesInTheWay(
        PlayerColor color, Coordinates rookStartingPosition, Coordinates kingMove, Coordinates blockingPosition
    ) {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece rightRook = pieceFactory.rook(color, rookStartingPosition);
        Piece king = pieceFactory.king(color);
        Piece blockingPiece = DefaultPieces.pawn(color, blockingPosition);

        Coordinates initialKingPosition = king.getCoordinates();

        Judge judge = new Judge(new BoardState(rightRook, king, blockingPiece));
        judge.submitMove(king, kingMove);

        assertEquals(initialKingPosition, king.getCoordinates());
        assertEquals(rookStartingPosition, rightRook.getCoordinates());
    }

    @ParameterizedTest
    @MethodSource("threatenedCastlingTestCases")
    public void castlingCannotHappenIfTheKingWouldMoveThroughAThreatenedcoordinates(
        PlayerColor color, Coordinates rookStartingPosition, Coordinates kingMove, Coordinates threateningPosition
    ) {
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();
        Piece leftRook = pieceFactory.rook(color, rookStartingPosition);
        Piece king = pieceFactory.king(color);
        Coordinates initialKingPosition = king.getCoordinates();
        Piece threat = pieceFactory.rook(color.opposite(), threateningPosition);

        Judge judge = new Judge(new BoardState(leftRook, king, threat));
        judge.submitMove(king, kingMove);

        assertEquals(initialKingPosition, king.getCoordinates());
        assertEquals(rookStartingPosition, leftRook.getCoordinates());
    }

    private static Stream<Arguments> castlingTestCases() {
        return Stream.of(
            Arguments.of(WHITE, A1, C1, D1),
            Arguments.of(WHITE, H1, G1, F1),
            Arguments.of(BLACK, A8, C8, D8),
            Arguments.of(BLACK, H8, G8, F8)
        );
    }

    private static Stream<Arguments> blockedCastlingTestCases() {
        return Stream.of(
            Arguments.of(WHITE, A1, C1, B1),
            Arguments.of(WHITE, A1, C1, D1),
            Arguments.of(WHITE, H1, G1, F1),
            Arguments.of(WHITE, H1, G1, G1),
            Arguments.of(BLACK, A8, C8, B8),
            Arguments.of(BLACK, A8, C8, D8),
            Arguments.of(BLACK, H8, G8, F8),
            Arguments.of(BLACK, H8, G8, G8)
        );
    }

    private static Stream<Arguments> threatenedCastlingTestCases() {
        return Stream.of(
            Arguments.of(WHITE, A1, C1, D4),
            Arguments.of(WHITE, A1, C1, C4),
            Arguments.of(WHITE, H1, G1, F4),
            Arguments.of(WHITE, H1, G1, G4),
            Arguments.of(BLACK, A8, C8, D5),
            Arguments.of(BLACK, A8, C8, C5),
            Arguments.of(BLACK, H8, G8, G5),
            Arguments.of(BLACK, H8, G8, F5)
        );
    }

}
