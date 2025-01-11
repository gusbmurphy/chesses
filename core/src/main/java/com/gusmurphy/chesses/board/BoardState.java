package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.PieceColorAndMovement;

import java.util.*;

public class BoardState {

    private final HashMap<BoardCoordinates, PieceColorAndMovement> piecesByCoordinates = new HashMap<>();

    public void placePieceAt(PieceColorAndMovement pieceColorAndMovement, BoardCoordinates coordinates) {
        piecesByCoordinates.put(coordinates, pieceColorAndMovement);
    }

    public Optional<PieceColorAndMovement> getPieceAt(BoardCoordinates coordinates) {
        return Optional.ofNullable(piecesByCoordinates.get(coordinates));
    }

    public Optional<PieceColorAndMovement> removePieceAt(BoardCoordinates coordinates) {
        PieceColorAndMovement removedPieceColorAndMovement = piecesByCoordinates.remove(coordinates);
        return Optional.ofNullable(removedPieceColorAndMovement);
    }

    public List<PieceAndCoordinates> getAllPieces() {
        List<PieceAndCoordinates> piecesAndCoordinates = new ArrayList<>();

        piecesByCoordinates.forEach((key, value) ->
            piecesAndCoordinates.add(new PieceAndCoordinates(value, key))
        );

        return piecesAndCoordinates;
    }

    public Optional<BoardCoordinates> coordinatesForPiece(PieceColorAndMovement pieceColorAndMovement) {
        Optional<Map.Entry<BoardCoordinates, PieceColorAndMovement>> entry = piecesByCoordinates
            .entrySet().stream().filter(e -> e.getValue() == pieceColorAndMovement).findFirst();

        return entry.map(Map.Entry::getKey);
    }

}
