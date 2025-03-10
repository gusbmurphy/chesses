package com.gusmurphy.chesses.rules;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.judge.Judge;

public class Variation {

    public final BoardState board;
    public final Judge judge;

    Variation(BoardState board, Judge judge) {
        this.board = board;
        this.judge = judge;
    }

}
