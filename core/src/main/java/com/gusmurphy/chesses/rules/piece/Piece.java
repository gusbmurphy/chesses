package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.PieceEventListener;
import com.gusmurphy.chesses.rules.board.BoardStateEventManager;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.movement.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.PieceAwareMovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.PlayerColor;

import java.util.List;

public class Piece {

    private final PlayerColor color;
    private final MovementStrategy movementStrategy;
    private BoardCoordinates coordinates;
    private BoardStateEventManager eventManager;
    private final PieceType type;

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

    public List<Move> currentPossibleMoves() {
        return movementStrategy.possibleMovesFrom(coordinates);
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
        eventManager.notify(PieceEvent.MOVED, this);
    }

    public void take() {
        eventManager.notify(PieceEvent.TAKEN, this);
    }

    public void setEventManager(BoardStateEventManager manager) {
        eventManager = manager;

        if (movementStrategy instanceof PieceAwareMovementStrategy) {
            manager.subscribe((PieceEventListener) movementStrategy, PieceEvent.MOVED);
        }
    }

}
