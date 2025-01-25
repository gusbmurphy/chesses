package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;

import java.util.List;

public abstract class JudgeDecorator extends Judge {

    private final Judge wrappedJudge;

    JudgeDecorator(Judge judge) {
        super(judge.boardState);
        wrappedJudge = judge;
    }

    @Override
    public void submitMove(Piece piece, Coordinates spot) {
        wrappedJudge.submitMove(piece, spot);
    }

    @Override
    public List<PieceMove> getPossibleMoves() {
        return wrappedJudge.getPossibleMoves();
    }
}
