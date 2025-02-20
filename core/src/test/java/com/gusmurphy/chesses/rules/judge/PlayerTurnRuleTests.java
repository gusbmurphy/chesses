package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceFactory;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.move.StaticMove;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTurnRuleTests {

    private final PieceFactory pieceFactory = new PieceFactory();

    @ParameterizedTest
    @MethodSource("oppositeColorPairs")
    void nothingHappensIfAMoveIsSubmittedForAPieceWithoutTheCurrentTurnsColor(
        PlayerColor pieceColor, PlayerColor currentTurnColor
    ) {
        TestJudge testJudge = new TestJudge();
        Judge turnAwareJudge = new PlayerTurnRule(testJudge, currentTurnColor);

        Piece piece = pieceFactory.rook(pieceColor, C4);
        turnAwareJudge.submitMove(piece, C5);

        assertFalse(testJudge.getLastMovedPiece().isPresent());
    }

    @ParameterizedTest
    @EnumSource(PlayerColor.class)
    void aMoveIsPassedAlongIfItIsForTheCorrectColor(PlayerColor color) {
        TestJudge testJudge = new TestJudge();
        Judge turnAwareJudge = new PlayerTurnRule(testJudge, color);

        Piece piece = pieceFactory.rook(color, C4);
        turnAwareJudge.submitMove(piece, C5);

        assertEquals(piece, testJudge.getLastMovedPiece().get());
    }

    @ParameterizedTest
    @MethodSource("oppositeColorPairs")
    void theTurnColorAlternates(
        PlayerColor currentTurnColor, PlayerColor turnColorAfterMove
    ) {
        TestJudge testJudge = new TestJudge();
        Judge turnAwareJudge = new PlayerTurnRule(testJudge, currentTurnColor);

        TestTurnChangeListener turnEventListener = new TestTurnChangeListener();
        turnAwareJudge.subscribeToTurnChange(turnEventListener);

        Piece piece = pieceFactory.rook(currentTurnColor, C4);
        turnAwareJudge.submitMove(piece, C5);

        assertEquals(turnColorAfterMove, turnEventListener.getLatestTurnColor().get());
    }

    @ParameterizedTest
    @MethodSource("oppositeColorPairs")
    void noMovesArePossibleForAPieceWithoutTheCurrentTurnColor(PlayerColor currentTurnColor, PlayerColor pieceColor) {
        TestJudge testJudge = new TestJudge();
        testJudge.setPossibleMoves(Collections.singletonList(new Move(new StaticMove(C5), pieceFactory.king(pieceColor, C4))));
        Judge turnAwareJudge = new PlayerTurnRule(testJudge, currentTurnColor);

        TestTurnChangeListener turnEventListener = new TestTurnChangeListener();
        turnAwareJudge.subscribeToTurnChange(turnEventListener);

        List<Move> possibleMoves = turnAwareJudge.getPossibleMoves();

        assertTrue(possibleMoves.isEmpty());
    }

    private static Stream<Arguments> oppositeColorPairs() {
        return Stream.of(
            Arguments.of(PlayerColor.WHITE, PlayerColor.BLACK),
            Arguments.of(PlayerColor.BLACK, PlayerColor.WHITE)
        );
    }

}
