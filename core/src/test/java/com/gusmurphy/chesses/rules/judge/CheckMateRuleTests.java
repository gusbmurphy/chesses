package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class CheckMateRuleTests {

    @Test
    void ifThereAreNoLegalMovesAfterAMoveThatMeansCheckmate() {
        TestJudge testJudge = new TestJudge();
        testJudge.setPossibleMoves(Collections.emptyList());

        Judge judge = new CheckMateRule(testJudge);
        TestGameOverListener listener = new TestGameOverListener();
        judge.subscribeToGameOver(listener);

        judge.submitMove(DefaultPieces.king(PlayerColor.BLACK, Coordinates.D1), Coordinates.D2);

        GameOverEvent latestEvent = listener.getLatestEvent();
        Assertions.assertEquals(GameOverEventType.CHECKMATE, latestEvent.type());
        Assertions.assertEquals(PlayerColor.BLACK, latestEvent.relevantColor());
    }

}
