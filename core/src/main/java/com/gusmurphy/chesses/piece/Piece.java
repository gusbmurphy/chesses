package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardStateEvent;
import com.gusmurphy.chesses.board.BoardStateEventManager;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.judge.MovementStrategy;
import com.gusmurphy.chesses.judge.PossibleMove;
import com.gusmurphy.chesses.player.PlayerColor;

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
    }

    public Piece(MovementStrategy strategy, BoardCoordinates coordinates) {
        this(
            new PieceColorAndMovement(PlayerColor.WHITE, strategy),
            coordinates,
            PieceType.KING
        );
    }

    public List<PossibleMove> currentPossibleMoves() {
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
        eventManager.notify(BoardStateEvent.PIECE_MOVED, this);
    }

    public void setEventManager(BoardStateEventManager manager) {
        eventManager = manager;
    }

}
