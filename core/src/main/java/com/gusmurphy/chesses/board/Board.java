package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.Piece;

import java.util.HashMap;
import java.util.Optional;

public class Board {

    private final HashMap<BoardCoordinates, Piece> piecesByCoordinates = new HashMap<>();

    public void placePieceAt(Piece piece, BoardCoordinates coordinates) {
        piecesByCoordinates.put(coordinates, piece);
    }

    public Optional<Piece> getPieceAt(BoardCoordinates coordinates) {
        return Optional.ofNullable(piecesByCoordinates.get(coordinates));
    }

}
