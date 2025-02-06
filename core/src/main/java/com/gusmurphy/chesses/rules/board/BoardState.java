package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.*;
import java.util.stream.Collectors;

public class BoardState {

    private final List<Piece> piecesOnBoard = new ArrayList<>();
    private final static SpotState EMPTY_SPOT = new EmptySpot();
    private final HashMap<Coordinates, SpotState> specialStates = new HashMap<>();

    public BoardState(Piece... pieces) {
        piecesOnBoard.addAll(Arrays.asList(pieces));
    }

    public BoardState(BoardState other) {
        piecesOnBoard.addAll(
            other.piecesOnBoard
                .stream()
                .map(Piece::new)
                .collect(Collectors.toList())
        );
    }

    public void place(Piece piece) {
        piecesOnBoard.add(piece);
    }

    public SpotState getStateAt(Coordinates coordinates) {
        Optional<Piece> piece = piecesOnBoard
            .stream()
            .filter(p -> p.getCoordinates() == coordinates)
            .findFirst();

        if (piece.isPresent()) {
            return new OccupiedSpot(piece.get());
        }

        return specialStates.getOrDefault(coordinates, EMPTY_SPOT);
    }

    public Optional<Piece> removePieceAt(Coordinates coordinates) {
        Optional<Piece> piece = piecesOnBoard
            .stream()
            .filter(p -> p.getCoordinates() == coordinates)
            .findFirst();

        piece.ifPresent(piecesOnBoard::remove);

        return piece;
    }

    public List<Piece> getAllPieces() {
        return piecesOnBoard;
    }

    public void setSpotState(Coordinates coordinates, SpotState state) {
        specialStates.put(coordinates, state);
    }

}
