package com.gusmurphy.chesses.rules;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.judge.Judge;

public class GameVariation {

    public final BoardState board;
    public final Judge judge;

    GameVariation(BoardState board, Judge judge) {
        this.board = board;
        this.judge = judge;
    }

}
