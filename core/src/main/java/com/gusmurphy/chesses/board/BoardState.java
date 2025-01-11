package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.piece.PieceColorAndMovement;

import java.util.*;

public class BoardState {

    private final List<Piece> piecesOnBoard = new ArrayList<>();

    public void place(Piece piece) {
        piecesOnBoard.add(piece);
    }

    public void placePieceAt(PieceColorAndMovement pieceColorAndMovement, BoardCoordinates coordinates) {
        piecesOnBoard.add(new Piece(pieceColorAndMovement, coordinates));
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

    public Optional<BoardCoordinates> coordinatesForPiece(PieceColorAndMovement pieceColorAndMovement) {
        Optional<Piece> piece = piecesOnBoard.stream().filter(p -> p.getPiece() == pieceColorAndMovement).findFirst();
        return piece.map(Piece::getCoordinates);
    }

}
