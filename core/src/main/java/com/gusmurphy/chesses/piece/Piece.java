package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardStateEvent;
import com.gusmurphy.chesses.board.BoardStateEventManager;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.judge.MovementStrategy;
import com.gusmurphy.chesses.judge.PossibleMove;
import com.gusmurphy.chesses.player.PlayerColor;

import java.util.List;

public class Piece {

    private final PieceColorAndMovement pieceColorAndMovement;
    private BoardCoordinates coordinates;
    private BoardStateEventManager eventManager;
    private final PieceType type;

    // TODO: These constructors are ugly
    public Piece(PieceColorAndMovement pieceColorAndMovement, BoardCoordinates coordinates, PieceType type) {
        this.pieceColorAndMovement = pieceColorAndMovement;
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
        return pieceColorAndMovement.movementStrategy().possibleMovesFrom(coordinates);
    }

    public BoardCoordinates getCoordinates() {
        return coordinates;
    }

    public PlayerColor color() {
        return pieceColorAndMovement.color();
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
