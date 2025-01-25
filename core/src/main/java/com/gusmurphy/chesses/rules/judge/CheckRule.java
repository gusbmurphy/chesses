package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;

import java.util.List;
import java.util.stream.Collectors;

public class CheckRule extends JudgeDecorator {

    public CheckRule(Judge judge) {
        super(judge);
    }

    @Override
    public List<PieceMove> getPossibleMoves() {
        List<PieceMove> moves = super.getPossibleMoves();

        return moves.stream().filter(move -> {
            BoardState boardCopy = new BoardState(this.boardState);
            Judge futureJudge = new Judge(boardCopy);
            Piece futurePiece = futureJudge.boardState.getPieceAt(move.getMovingPiece().getCoordinates()).get();
            futureJudge.submitMove(futurePiece, move.spot());
            List<PieceMove> possibleMovesAfter = futureJudge.getPossibleMoves();
            return possibleMovesAfter.stream().noneMatch(futureMove ->
                    futureMove.takes().isPresent() &&
                        futureMove.takes().get().isCheckable() &&
                        futureMove.takes().get().color() == move.getMovingPiece().color()
            );
        }).collect(Collectors.toList());
    }

}
