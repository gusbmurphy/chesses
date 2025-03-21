package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.square.SquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EnPassantMove implements UnassociatedMove {

    private final Coordinates to;
    private final Coordinates effectedCoordinates;
    private final SquareState effectedState;

    public EnPassantMove(Coordinates to, Coordinates effectedCoordinates, SquareState effectedState) {
        this.to = to;
        this.effectedCoordinates = effectedCoordinates;
        this.effectedState = effectedState;
    }

    @Override
    public Map<Coordinates, SquareState> effectedSquares() {
        Map<Coordinates, SquareState> effectedCoordinates = new HashMap<>();
        effectedCoordinates.put(this.effectedCoordinates, effectedState);
        return effectedCoordinates;
    }

    @Override
    public Coordinates coordinates() {
        return to;
    }

    @Override
    public Optional<UnassociatedMove> next() {
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
