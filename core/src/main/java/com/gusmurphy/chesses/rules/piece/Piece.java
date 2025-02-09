package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.PieceEventListener;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.strategy.MovementStrategy;
import com.gusmurphy.chesses.rules.PlayerColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Piece {

    private final PlayerColor color;
    private MovementStrategy movementStrategy;
    private Coordinates coordinates;
    private final PieceType type;
    private final List<PieceEventListener> eventListeners = new ArrayList<>();

    // TODO: These constructors are ugly
    public Piece(
        PlayerColor color,
        MovementStrategy movementStrategy,
        Coordinates coordinates,
        PieceType type
    ) {
        this.color = color;
        this.movementStrategy = movementStrategy;
        this.movementStrategy.setRelevantPiece(this);
        this.coordinates = coordinates;
        this.type = type;

        eventListeners.add(movementStrategy);
    }

    public Piece(MovementStrategy strategy, Coordinates coordinates) {
        this(
            PlayerColor.WHITE,
            strategy,
            coordinates,
            PieceType.KING
        );
    }

    public Piece(PlayerColor color, Coordinates coordinates, PieceType type) {
        this.color = color;
        this.coordinates = coordinates;
        this.type = type;
    }

    public Piece(Piece other) {
        color = other.color;
        movementStrategy = other.movementStrategy;
        coordinates = other.coordinates;
        type = other.type;
    }

    public boolean isCheckable() {
        return type == PieceType.KING;
    }

    public void subscribeToEvents(PieceEventListener listener) {
        eventListeners.add(listener);
    }

    public List<Move> currentPossibleMoves() {
        return movementStrategy
            .possibleMovesFrom(coordinates)
            .stream()
            .map(move -> new Move(move, this))
            .collect(Collectors.toList());
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public PlayerColor color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    public void moveTo(Coordinates coordinates) {
        this.coordinates = coordinates;
        eventListeners.forEach(listener -> listener.onPieceEvent(PieceEvent.MOVED, this));
    }

    public void take() {
        eventListeners.forEach(listener -> listener.onPieceEvent(PieceEvent.TAKEN, this));
    }

    protected void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

}
