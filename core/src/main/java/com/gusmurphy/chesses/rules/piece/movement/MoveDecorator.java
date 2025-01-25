package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public abstract class MoveDecorator implements Move {

    private final Move wrappedMove;

    MoveDecorator(Move move) {
        wrappedMove = move;
    }

    @Override
    public Coordinates spot() {
        return wrappedMove.spot();
    }

    @Override
    public Optional<Move> next() {
        return wrappedMove.next();
    }

    @Override
    public Optional<Piece> takes() {
        return wrappedMove.takes();
    }

}
