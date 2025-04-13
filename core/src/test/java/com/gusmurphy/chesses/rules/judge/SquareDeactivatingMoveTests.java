package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

        Judge baseJudge = new Judge(new BoardState(rook, bishop));
        Judge squareDeactivationJudge = new SquareDeactivationRule(baseJudge);

        squareDeactivationJudge.submitMove(rook, C5);
        squareDeactivationJudge.submitMove(rook, H5);

        assertThrows(IllegalMoveException.class, () -> {
            squareDeactivationJudge.submitMove(bishop, C5);
        });

        assertEquals(E3, bishop.getCoordinates());
    }

    private static Stream<Arguments> oppositeColorPairs() {
        return Stream.of(
            Arguments.of(PlayerColor.WHITE, PlayerColor.BLACK),
            Arguments.of(PlayerColor.BLACK, PlayerColor.WHITE)
        );
    }

}
