package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.Move;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestJudge implements Judge {

    private Piece lastMovedPiece;

    @Override
    public List<Move> possibleMovesFor(Piece piece) {
        return Collections.emptyList();
    }

    @Override
    public void submitMove(Piece piece, BoardCoordinates spot) {
        lastMovedPiece = piece;
    }

    public Optional<Piece> getLastMovedPiece() {
        return Optional.ofNullable(lastMovedPiece);
    }
}
