package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardStateEvent;
import com.gusmurphy.chesses.board.BoardStateEventManager;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinatesXyAdapter;

public class Piece {

    private final PieceColorAndMovement pieceColorAndMovement;
    private BoardCoordinates coordinates;
    private BoardStateEventManager eventManager;

    public Piece(PieceColorAndMovement pieceColorAndMovement, BoardCoordinates coordinates) {
        this.pieceColorAndMovement = pieceColorAndMovement;
        this.coordinates = coordinates;
    }

    public PieceColorAndMovement getPiece() {
        return pieceColorAndMovement;
    }

    public BoardCoordinates getCoordinates() {
        return coordinates;
    }

    public BoardCoordinatesXyAdapter getXyCoordinates() {
        return new BoardCoordinatesXyAdapter(coordinates);
    }

    public void moveTo(BoardCoordinates coordinates) {
        this.coordinates = coordinates;
        eventManager.notify(BoardStateEvent.PIECE_MOVED, this);
    }

    public void setEventManager(BoardStateEventManager manager) {
        eventManager = manager;
    }

}
