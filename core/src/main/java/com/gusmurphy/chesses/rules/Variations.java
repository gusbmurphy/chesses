package com.gusmurphy.chesses.rules;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.StartingBoards;
import com.gusmurphy.chesses.rules.judge.DefaultJudge;
import com.gusmurphy.chesses.rules.judge.Judge;

public class Variations {

    public static GameVariation standard() {
        BoardState boardState = StartingBoards.regular();
        Judge judge = new DefaultJudge(boardState);
        return new GameVariation(boardState, judge);
    }

}
