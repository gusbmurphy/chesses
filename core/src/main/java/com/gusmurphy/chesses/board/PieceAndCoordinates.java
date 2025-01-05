package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinatesXyAdapter;
import com.gusmurphy.chesses.piece.Piece;

public class PieceAndCoordinates {

    private final Piece piece;
    private final BoardCoordinates coordinates;

    PieceAndCoordinates(Piece piece, BoardCoordinates coordinates) {
        this.piece = piece;
        this.coordinates = coordinates;
    }

    public Piece getPiece() {
        return piece;
    }

    public BoardCoordinates getCoordinates() {
        return coordinates;
    }

    public BoardCoordinatesXyAdapter getXyCoordinates() {
        return new BoardCoordinatesXyAdapter(coordinates);
    }

}
