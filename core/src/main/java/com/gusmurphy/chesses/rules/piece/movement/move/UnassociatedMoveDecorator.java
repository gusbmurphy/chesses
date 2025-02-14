package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.square.SquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class UnassociatedMoveDecorator implements UnassociatedMove {

    private final UnassociatedMove wrappedMove;

    UnassociatedMoveDecorator(UnassociatedMove move) {
        wrappedMove = move;
    }

    @Override
    public Coordinates coordinates() {
        return wrappedMove.coordinates();
    }

    @Override
    public Optional<UnassociatedMove> next() {
        return wrappedMove.next();
    }

    @Override
    public Optional<Piece> takes() {
        return wrappedMove.takes();
    }

    @Override
    public boolean takeDisallowed() {
        return wrappedMove.takeDisallowed();
    }

    @Override
    public boolean mustTake() {
        return wrappedMove.mustTake();
    }

    @Override
    public Optional<Move> linkedMove() {
        return wrappedMove.linkedMove();
    }

    @Override
    public List<Coordinates> requiredUnoccupiedSpaces() {
        return wrappedMove.requiredUnoccupiedSpaces();
    }

    @Override
    public List<Coordinates> requiredSafeSpaces() {
        return wrappedMove.requiredSafeSpaces();
    }

    @Override
    public Map<Coordinates, SquareState> effectedcoordinatess() {
        return wrappedMove.effectedcoordinatess();
    }
}
