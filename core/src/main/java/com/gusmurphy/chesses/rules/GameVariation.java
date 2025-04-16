package com.gusmurphy.chesses.rules;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.StartingBoards;
import com.gusmurphy.chesses.rules.judge.*;

public class GameVariation {

    public final BoardState board;
    public final BaseJudge judge;
    public final String displayName;

    private GameVariation(BoardState board, BaseJudge judge, String displayName) {
        this.board = board;
        this.judge = judge;
        this.displayName = displayName;
    }

    public static GameVariation standard() {
        BoardState boardState = StartingBoards.regular();
        BaseJudge judge = new DefaultJudge(boardState);
        return new GameVariation(boardState, judge, "Standard");
    }

    public static GameVariation oopsAllSomething() {
        BoardState boardState = StartingBoards.oopsAllSomething();
        BaseJudge judge = new DefaultJudge(boardState);
        return new GameVariation(boardState, judge, "Oops all X");
    }

    public static GameVariation singlePlayer() {
        BoardState boardState = StartingBoards.singlePlayer();
        // Maybe we should have a JudgeBuilder...
        BaseJudge judge = new CheckMateRule(
            new CheckRule(
                new BaseJudge(boardState)
            )
        );
        return new GameVariation(boardState, judge, "SinglePlayer");
    }

    public static GameVariation moveEveryPiece() {
        BoardState boardState = StartingBoards.regular();
        BaseJudge judge = new CheckMateRule(new CheckRule(new PlayerTurnRule(new BaseJudge(boardState), PlayerColor.WHITE, 16)));
        return new GameVariation(boardState, judge, "Move Every Piece");
    }

}
