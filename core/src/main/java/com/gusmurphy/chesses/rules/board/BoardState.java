package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.*;
import java.util.stream.Collectors;

public class BoardState {

    private final List<Piece> piecesOnBoard = new ArrayList<>();

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

    public Optional<Piece> getPieceAt(Coordinates coordinates) {
        return piecesOnBoard.stream().filter(piece -> piece.getCoordinates() == coordinates).findFirst();
    }

    public Optional<Piece> removePieceAt(Coordinates coordinates) {
        Optional<Piece> piece = getPieceAt(coordinates);
        piece.ifPresent(piecesOnBoard::remove);
        return piece;
    }

    public List<Piece> getAllPieces() {
        return piecesOnBoard;
    }

}
