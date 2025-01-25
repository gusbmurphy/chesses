package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestJudge extends Judge {

    private Piece lastMovedPiece;
    private List<PieceMove> possibleMoves = Collections.emptyList();

    public TestJudge() {
        super(new BoardState());
    }

    public void setPossibleMoves(List<PieceMove> moves) {
        possibleMoves = moves;
    }

    @Override
    public void submitMove(Piece piece, BoardCoordinates spot) {
        lastMovedPiece = piece;
    }

    @Override
    public List<PieceMove> getPossibleMoves() {
        return possibleMoves;
    }

    public Optional<Piece> getLastMovedPiece() {
        return Optional.ofNullable(lastMovedPiece);
    }
}
