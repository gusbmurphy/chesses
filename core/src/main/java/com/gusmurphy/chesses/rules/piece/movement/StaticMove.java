package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class StaticMove extends Move {
    private final BoardCoordinates spot;

    public StaticMove(BoardCoordinates spot) {
        this.spot = spot;
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
        return Optional.empty();
    }
}
