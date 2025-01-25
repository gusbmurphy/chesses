package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public interface Move {

    Coordinates spot();
    Optional<Move> next();
    Optional<Piece> takes();
    boolean mustTake();

    default boolean takeDisallowed() {
        return false;
    }

}
