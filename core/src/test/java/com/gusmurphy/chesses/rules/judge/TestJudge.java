package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestJudge extends Judge {

    private Piece lastMovedPiece;
    private List<Move> possibleMoves = Collections.emptyList();

    public TestJudge() {
        super(new BoardState());
    }

    public void setPossibleMoves(List<Move> moves) {
        possibleMoves = moves;
    }

    @Override
    public void submitMove(Piece piece, Coordinates coordinates) {
        lastMovedPiece = piece;
    }

    @Override
    public List<Move> getPossibleMoves() {
        return possibleMoves;
    }

    public Optional<Piece> getLastMovedPiece() {
        return Optional.ofNullable(lastMovedPiece);
    }
}
