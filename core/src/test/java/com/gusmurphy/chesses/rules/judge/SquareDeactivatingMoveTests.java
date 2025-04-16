package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.SpecialSquareState;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static org.junit.jupiter.api.Assertions.*;

public class SquareDeactivatingMoveTests {

    @ParameterizedTest
    @MethodSource("oppositeColorPairs")
    void afterAPieceHasMadeADeactivatingMoveAnotherPieceCannotMoveThere(PlayerColor firstColor, PlayerColor secondColor) {
        PieceFactory pieceFactory = new PieceFactory();
        Piece rook = pieceFactory.rook(firstColor, C3);
        Piece bishop = pieceFactory.bishop(secondColor, E3);

        BaseJudge baseJudge = new BaseJudge(new BoardState(rook, bishop));
        Judge squareDeactivationJudge = new SquareDeactivationRule(baseJudge);

        squareDeactivationJudge.submitMove(rook, C5);
        squareDeactivationJudge.submitMove(rook, H5);

        assertThrows(IllegalMoveException.class, () -> {
            squareDeactivationJudge.submitMove(bishop, C5);
        });

        assertEquals(E3, bishop.getCoordinates());
    }

    @Test
    void ifAMoveIsIllegalForOtherReasonsTheSpaceIsNotDeactivated() {
        PieceFactory pieceFactory = new PieceFactory();
        Piece rook = pieceFactory.rook(PlayerColor.BLACK, C3);
        Piece bishop = pieceFactory.bishop(PlayerColor.WHITE, E3);

        TestJudge baseJudge = new TestJudge(rook, bishop);
        Judge squareDeactivationJudge = new SquareDeactivationRule(baseJudge);

        baseJudge.throwOnNextMove();
        assertThrows(IllegalMoveException.class, () -> {
            squareDeactivationJudge.submitMove(rook, C5);
        });

        assertDoesNotThrow(() -> {
            squareDeactivationJudge.submitMove(bishop, C5);
        });
    }

    @Test
    void weCanGetTheCurrentlyDeactivatedSquares() {
        PieceFactory pieceFactory = new PieceFactory();
        Piece rook = pieceFactory.rook(PlayerColor.WHITE, C3);
        Piece bishop = pieceFactory.bishop(PlayerColor.WHITE, E3);

        BaseJudge baseJudge = new BaseJudge(new BoardState(rook, bishop));
        Judge squareDeactivationJudge = new SquareDeactivationRule(baseJudge);

        squareDeactivationJudge.submitMove(rook, C5);
        squareDeactivationJudge.submitMove(bishop, H6);

        List<SpecialSquareState> results = squareDeactivationJudge.getSpecialSquareStates();
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(square -> square.coordinates() == C5));
        assertTrue(results.stream().anyMatch(square -> square.coordinates() == H6));
    }

    private static Stream<Arguments> oppositeColorPairs() {
        return Stream.of(
            Arguments.of(PlayerColor.WHITE, PlayerColor.BLACK),
            Arguments.of(PlayerColor.BLACK, PlayerColor.WHITE)
        );
    }

}
