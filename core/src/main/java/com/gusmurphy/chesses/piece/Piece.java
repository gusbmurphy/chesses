package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinatesXyAdapter;

public class Piece {

    private final PieceColorAndMovement pieceColorAndMovement;
    private final BoardCoordinates coordinates;

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

}
