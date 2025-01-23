package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates.C4;
import static com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates.C5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTurnRuleTests {

    @ParameterizedTest
    @MethodSource("oppositeColorPairs")
    void nothingHappensIfAMoveIsSubmittedForAPieceWithoutTheCurrentTurnsColor(
        PlayerColor pieceColor, PlayerColor currentTurnColor
    ) {
        TestJudge testJudge = new TestJudge();
        Judge turnAwareJudge = new PlayerTurnRule(testJudge, currentTurnColor);

        Piece piece = DefaultPieces.rook(pieceColor, C4);
        turnAwareJudge.submitMove(piece, C5);

        assertFalse(testJudge.getLastMovedPiece().isPresent());
    }

    @ParameterizedTest
    @EnumSource(PlayerColor.class)
    void aMoveIsPassedAlongIfItIsForTheCorrectColor(PlayerColor color) {
        TestJudge testJudge = new TestJudge();
        Judge turnAwareJudge = new PlayerTurnRule(testJudge, color);

        Piece piece = DefaultPieces.rook(color, C4);
        turnAwareJudge.submitMove(piece, C5);

        assertEquals(piece, testJudge.getLastMovedPiece().get());
    }

    private static Stream<Arguments> oppositeColorPairs() {
        return Stream.of(
            Arguments.of(PlayerColor.WHITE, PlayerColor.BLACK),
            Arguments.of(PlayerColor.BLACK, PlayerColor.WHITE)
        );
    }

}
