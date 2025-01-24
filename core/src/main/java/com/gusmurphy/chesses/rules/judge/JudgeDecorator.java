package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;

import java.util.List;

public abstract class JudgeDecorator extends BaseJudge {

    private final Judge wrappedJudge;

    JudgeDecorator(Judge judge) {
        wrappedJudge = judge;
    }

    @Override
    public List<Move> possibleMovesFor(Piece piece) {
        return wrappedJudge.possibleMovesFor(piece);
    }

    @Override
    public void submitMove(Piece piece, BoardCoordinates spot) {
        wrappedJudge.submitMove(piece, spot);
    }

    @Override
    public List<PieceMove> getPossibleMoves() {
        return wrappedJudge.getPossibleMoves();
    }
}
