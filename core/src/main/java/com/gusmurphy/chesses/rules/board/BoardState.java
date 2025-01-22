package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.*;

public class BoardState {

    private final List<Piece> piecesOnBoard = new ArrayList<>();

    public BoardState(Piece... pieces) {
        piecesOnBoard.addAll(Arrays.asList(pieces));
    }

    public void place(Piece piece) {
        piecesOnBoard.add(piece);
    }

    public Optional<Piece> getPieceAt(BoardCoordinates coordinates) {
        return piecesOnBoard.stream().filter(piece -> piece.getCoordinates() == coordinates).findFirst();
    }

    public Optional<Piece> removePieceAt(BoardCoordinates coordinates) {
        Optional<Piece> piece = getPieceAt(coordinates);
        piece.ifPresent(piecesOnBoard::remove);
        return piece;
    }

    public List<Piece> getAllPieces() {
        return piecesOnBoard;
    }

    public boolean spotIsFree(BoardCoordinates spot) {
        return piecesOnBoard.stream().noneMatch(piece -> piece.getCoordinates() == spot);
    }

}
