package com.gusmurphy.chesses.rules;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.StartingBoards;
import com.gusmurphy.chesses.rules.judge.*;

public class GameVariation {

    public final BoardState board;
    public final Judge judge;
    public final String displayName;

    private GameVariation(BoardState board, Judge judge, String displayName) {
        this.board = board;
        this.judge = judge;
        this.displayName = displayName;
    }

    public static GameVariation standard() {
        BoardState boardState = StartingBoards.regular();
        Judge judge = new DefaultJudge(boardState);
        return new GameVariation(boardState, judge, "Standard");
    }

    public static GameVariation singlePlayer() {
        BoardState boardState = StartingBoards.singlePlayer();
        // Maybe we should have a JudgeBuilder...
        Judge judge = new CheckMateRule(
            new CheckRule(
                new Judge(boardState)
            )
        );
        return new GameVariation(boardState, judge, "SinglePlayer");
    }

    public static GameVariation moveEveryPiece() {
        BoardState boardState = StartingBoards.regular();
        Judge judge = new CheckMateRule(new CheckRule(new PlayerTurnRule(new Judge(boardState), PlayerColor.WHITE, 16)));
        return new GameVariation(boardState, judge, "Move Every Piece");
    }

}
