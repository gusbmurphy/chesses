package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.piece.movement.PieceMove;

import java.util.Collections;
import java.util.List;

public class CheckRule extends JudgeDecorator {

    CheckRule(Judge judge) {
        super(judge);
    }

    @Override
    public List<PieceMove> getPossibleMoves() {
        return Collections.emptyList();
    }

}
