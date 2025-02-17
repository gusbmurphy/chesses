package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.board.square.EmptySquare;
import com.gusmurphy.chesses.rules.board.square.OccupiedSquare;
import com.gusmurphy.chesses.rules.board.square.SquareState;
import com.gusmurphy.chesses.rules.judge.TurnChangeListener;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.*;
import java.util.stream.Collectors;

public class BoardState implements TurnChangeListener {

    private final List<Piece> piecesOnBoard = new ArrayList<>();
    private final static SquareState EMPTY_SQUARE = new EmptySquare();
    private final HashMap<Coordinates, SquareState> specialSquares = new HashMap<>();
    private final List<TurnChangeListener> childTurnChangeListeners = new ArrayList<>();
    private int latestBoardId = 0;

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

    public SquareState getStateAt(Coordinates coordinates) {
        Optional<Piece> piece = piecesOnBoard
            .stream()
            .filter(p -> p.getCoordinates() == coordinates)
            .findFirst();

        if (piece.isPresent()) {
            return new OccupiedSquare(piece.get());
        }

        return specialSquares.getOrDefault(coordinates, EMPTY_SQUARE);
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

    public void setSquareState(Coordinates coordinates, SquareState state) {
        childTurnChangeListeners.add(state);
        specialSquares.put(coordinates, state);
    }

    @Override
    public void onTurnChange(PlayerColor newTurnColor) {
        childTurnChangeListeners.forEach(listener -> listener.onTurnChange(newTurnColor));
    }

    public int getNextId() {
        int idToReturn = latestBoardId;
        latestBoardId++;
        return idToReturn;
    }
}
