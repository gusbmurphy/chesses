package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.square.SpotState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UnassociatedMove {

    Coordinates spot();
    Optional<UnassociatedMove> next();
    Optional<Piece> takes();
    boolean mustTake();

    default boolean takeDisallowed() {
        return false;
    }

    default Optional<Move> linkedMove() {
        return Optional.empty();
    }

    default List<Coordinates> requiredUnoccupiedSpaces() {
        return Collections.emptyList();
    }

    default List<Coordinates> requiredSafeSpaces() {
        return Collections.emptyList();
    }

    default Map<Coordinates, SpotState> effectedSpots() {
        return Collections.emptyMap();
    }

}
