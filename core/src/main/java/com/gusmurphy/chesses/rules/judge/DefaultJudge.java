package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;

public class DefaultJudge extends JudgeDecorator {

    public DefaultJudge(BoardState boardState) {
        super(new CheckMateRule(new CheckRule(new PlayerTurnRule(new BaseJudge(boardState), PlayerColor.WHITE))));
    }

}
