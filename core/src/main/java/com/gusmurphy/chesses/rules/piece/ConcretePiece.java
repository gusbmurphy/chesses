package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.BoardStateEvent;
import com.gusmurphy.chesses.rules.board.BoardStateEventListener;
import com.gusmurphy.chesses.rules.board.BoardStateEventManager;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.movement.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.PieceAwareMovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.PlayerColor;

import java.util.List;

public class ConcretePiece implements Piece {

    private final PlayerColor color;
    private final MovementStrategy movementStrategy;
    private BoardCoordinates coordinates;
    private BoardStateEventManager eventManager;
    private final PieceType type;

    // TODO: These constructors are ugly
    public ConcretePiece(PieceColorAndMovement pieceColorAndMovement, BoardCoordinates coordinates, PieceType type) {
        this.color = pieceColorAndMovement.color();
        this.movementStrategy = pieceColorAndMovement.movementStrategy();
        this.coordinates = coordinates;
        this.type = type;

        if (this.movementStrategy instanceof PieceAwareMovementStrategy) {
            ((PieceAwareMovementStrategy) this.movementStrategy).setRelevantPiece(this);
        }
    }

    public ConcretePiece(
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

    public ConcretePiece(MovementStrategy strategy, BoardCoordinates coordinates) {
        this(
            new PieceColorAndMovement(PlayerColor.WHITE, strategy),
            coordinates,
            PieceType.KING
        );
    }

    @Override
    public List<Move> currentPossibleMoves() {
        return movementStrategy.possibleMovesFrom(coordinates);
    }

    @Override
    public BoardCoordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public PlayerColor color() {
        return color;
    }

    @Override
    public PieceType type() {
        return type;
    }

    @Override
    public void moveTo(BoardCoordinates coordinates) {
        this.coordinates = coordinates;
        eventManager.notify(BoardStateEvent.PIECE_MOVED, this);
    }

    @Override
    public void take() {
        eventManager.notify(BoardStateEvent.PIECE_TAKEN, this);
    }

    @Override
    public void setEventManager(BoardStateEventManager manager) {
        eventManager = manager;

        if (movementStrategy instanceof PieceAwareMovementStrategy) {
            manager.subscribe((BoardStateEventListener) movementStrategy, BoardStateEvent.PIECE_MOVED);
        }
    }

}
