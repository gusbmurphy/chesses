package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.King;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BoardState {

    private final HashMap<BoardCoordinates, King> piecesByCoordinates = new HashMap<>();

    public void placePieceAt(King piece, BoardCoordinates coordinates) {
        piecesByCoordinates.put(coordinates, piece);
    }

    public Optional<King> getPieceAt(BoardCoordinates coordinates) {
        return Optional.ofNullable(piecesByCoordinates.get(coordinates));
    }

    public Optional<King> removePieceAt(BoardCoordinates coordinates) {
        King removedPiece = piecesByCoordinates.remove(coordinates);
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
