package com.gusmurphy.chesses.piece.movement;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.Optional;

public class PossibleStaticMove implements PossibleMove {
    private final BoardCoordinates spot;

    public PossibleStaticMove(BoardCoordinates spot) {
        this.spot = spot;
    }

    @Override
    public BoardCoordinates spot() {
        return spot;
    }

    @Override
    public Optional<PossibleMove> next() {
        return Optional.empty();
    }
}
