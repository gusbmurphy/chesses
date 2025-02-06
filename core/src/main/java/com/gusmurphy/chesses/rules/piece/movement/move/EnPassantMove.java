package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.SpotState;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EnPassantMove implements Move {

    private final Coordinates to;
    private final Coordinates effectedCoordinates;
    private final SpotState effectedState;

    public EnPassantMove(Coordinates to, Coordinates effectedCoordinates, SpotState effectedState) {
        this.to = to;
        this.effectedCoordinates = effectedCoordinates;
        this.effectedState = effectedState;
    }

    @Override
    public Map<Coordinates, SpotState> effectedSpots() {
        Map<Coordinates, SpotState> effectedSpots = new HashMap<>();
        effectedSpots.put(effectedCoordinates, effectedState);
        return effectedSpots;
    }

    @Override
    public Coordinates spot() {
        return to;
    }

    @Override
    public Optional<Move> next() {
        return Optional.empty();
    }

    @Override
    public Optional<Piece> takes() {
        return Optional.empty();
    }

    @Override
    public boolean mustTake() {
        return false;
    }

}
