package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.PieceEventListener;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.movement.MoveWithPiece;
import com.gusmurphy.chesses.rules.piece.movement.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.PieceAwareMovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.PlayerColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Piece {

    private final PlayerColor color;
    private final MovementStrategy movementStrategy;
    private BoardCoordinates coordinates;
    private final PieceType type;
    private final List<PieceEventListener> eventListeners = new ArrayList<>();

    // TODO: These constructors are ugly
    public Piece(PieceColorAndMovement pieceColorAndMovement, BoardCoordinates coordinates, PieceType type) {
        this.color = pieceColorAndMovement.color();
        this.movementStrategy = pieceColorAndMovement.movementStrategy();
        this.coordinates = coordinates;
        this.type = type;

        if (this.movementStrategy instanceof PieceAwareMovementStrategy) {
            ((PieceAwareMovementStrategy) this.movementStrategy).setRelevantPiece(this);
        }
    }

    public Piece(
        PlayerColor color,
        MovementStrategy movementStrategy,
        BoardCoordinates coordinates,
        PieceType type
    ) {
        this.color = color;
        this.movementStrategy = movementStrategy;
        this.coordinates = coordinates;
        this.type = type;

        if (this.movementStrategy instanceof PieceAwareMovementStrategy) {
            ((PieceAwareMovementStrategy) this.movementStrategy).setRelevantPiece(this);
        }
    }

    public Piece(MovementStrategy strategy, BoardCoordinates coordinates) {
        this(
            new PieceColorAndMovement(PlayerColor.WHITE, strategy),
            coordinates,
            PieceType.KING
        );
    }

    public void subscribeToEvents(PieceEventListener listener) {
        eventListeners.add(listener);
    }

    public List<MoveWithPiece> currentPossibleMoves() {
        return movementStrategy
            .possibleMovesFrom(coordinates)
            .stream()
            .map(move -> new MoveWithPiece(move, this))
            .collect(Collectors.toList());
    }

    public BoardCoordinates getCoordinates() {
        return coordinates;
    }

    public PlayerColor color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    public void moveTo(BoardCoordinates coordinates) {
        this.coordinates = coordinates;
        eventListeners.forEach(listener -> listener.onPieceEvent(PieceEvent.MOVED, this));
    }

    public void take() {
        eventListeners.forEach(listener -> listener.onPieceEvent(PieceEvent.TAKEN, this));
    }

}
