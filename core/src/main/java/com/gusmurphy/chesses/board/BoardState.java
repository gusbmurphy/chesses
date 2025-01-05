package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BoardState {

    private final HashMap<BoardCoordinates, Piece> piecesByCoordinates = new HashMap<>();

    public void placePieceAt(Piece piece, BoardCoordinates coordinates) {
        piecesByCoordinates.put(coordinates, piece);
    }

    public Optional<Piece> getPieceAt(BoardCoordinates coordinates) {
        return Optional.ofNullable(piecesByCoordinates.get(coordinates));
    }

    public Optional<Piece> removePieceAt(BoardCoordinates coordinates) {
        Piece removedPiece = piecesByCoordinates.remove(coordinates);
        return Optional.ofNullable(removedPiece);
    }

    public List<PieceAndCoordinates> getAllPieces() {
        List<PieceAndCoordinates> piecesAndCoordinates = new ArrayList<>();

        piecesByCoordinates.forEach((key, value) ->
            piecesAndCoordinates.add(new PieceAndCoordinates(value, key))
        );

        return piecesAndCoordinates;
    }

}
