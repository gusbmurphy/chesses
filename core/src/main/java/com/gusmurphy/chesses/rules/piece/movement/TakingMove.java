package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class TakingMove extends Move {

    private final BoardCoordinates spot;
    private final Piece pieceToTake;

    public TakingMove(BoardCoordinates spot, Piece pieceToTake) {
        this.spot = spot;
        this.pieceToTake = pieceToTake;
    }

    @Override
    public BoardCoordinates spot() {
        return spot;
    }

    @Override
    public Optional<Move> next() {
        return Optional.empty();
    }

    @Override
    public Optional<Piece> takes() {
        return Optional.of(pieceToTake);
    }
}
