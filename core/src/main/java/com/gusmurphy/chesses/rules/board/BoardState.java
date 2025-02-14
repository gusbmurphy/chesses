package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.board.square.EmptySpot;
import com.gusmurphy.chesses.rules.board.square.OccupiedSpot;
import com.gusmurphy.chesses.rules.board.square.SpotState;
import com.gusmurphy.chesses.rules.judge.TurnChangeListener;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.*;
import java.util.stream.Collectors;

public class BoardState implements TurnChangeListener {

    private final List<Piece> piecesOnBoard = new ArrayList<>();
    private final static SpotState EMPTY_SPOT = new EmptySpot();
    private final HashMap<Coordinates, SpotState> specialStates = new HashMap<>();
    private final List<TurnChangeListener> childTurnChangeListeners = new ArrayList<>();

    public BoardState(Piece... pieces) {
        piecesOnBoard.addAll(Arrays.asList(pieces));
        piecesOnBoard.forEach(piece -> piece.setBoardState(this));
    }

    public BoardState(BoardState other) {
        piecesOnBoard.addAll(
            other.piecesOnBoard
                .stream()
                .map(Piece::new)
                .collect(Collectors.toList())
        );
        piecesOnBoard.forEach(piece -> piece.setBoardState(this));
    }

    public void place(Piece piece) {
        piece.setBoardState(this);
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
        childTurnChangeListeners.add(state);
        specialStates.put(coordinates, state);
    }

    @Override
    public void onTurnChange(PlayerColor newTurnColor) {
        childTurnChangeListeners.forEach(listener -> listener.onTurnChange(newTurnColor));
    }
}
