package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinatesXyAdapter;
import com.gusmurphy.chesses.piece.King;

public class PieceAndCoordinates {

    private final King piece;
    private final BoardCoordinates coordinates;

    PieceAndCoordinates(King piece, BoardCoordinates coordinates) {
        this.piece = piece;
        this.coordinates = coordinates;
    }

    public King getPiece() {
        return piece;
    }

    public BoardCoordinates getCoordinates() {
        return coordinates;
    }

    public BoardCoordinatesXyAdapter getXyCoordinates() {
        return new BoardCoordinatesXyAdapter(coordinates);
    }

}
