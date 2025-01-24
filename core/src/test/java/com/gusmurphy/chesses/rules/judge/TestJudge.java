package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.Move;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestJudge extends BaseJudge {

    private Piece lastMovedPiece;
    private List<Move> possibleMoves = Collections.emptyList();

    @Override
    public List<Move> possibleMovesFor(Piece piece) {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Move> moves) {
        possibleMoves = moves;
    }

    @Override
    public void submitMove(Piece piece, BoardCoordinates spot) {
        lastMovedPiece = piece;
    }

    public Optional<Piece> getLastMovedPiece() {
        return Optional.ofNullable(lastMovedPiece);
    }
}
