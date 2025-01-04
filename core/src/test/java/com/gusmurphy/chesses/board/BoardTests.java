package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.King;
import com.gusmurphy.chesses.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BoardTests {

    @Test
    void aPieceCanBeAddedToTheBoard() {
        Piece piece = new King();
        BoardCoordinates coordinates = new BoardCoordinates(File.B, Rank.FIVE);
        Board board = new Board();

        board.placePieceAt(piece, coordinates);

        Optional<Piece> pieceOnBoard = board.getPieceAt(coordinates);

        Assertions.assertEquals(pieceOnBoard.get(), piece);
    }

    @Test
    void anEmptyOptionalIsReturnedForAnEmptyPosition() {
        Board board = new Board();
        BoardCoordinates coordinates = new BoardCoordinates(File.B, Rank.FIVE);

        Optional<Piece> result = board.getPieceAt(coordinates);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void aPieceCanBeRemoved() {
        Piece piece = new King();
        BoardCoordinates coordinates = new BoardCoordinates(File.B, Rank.FIVE);
        Board board = new Board();

        board.placePieceAt(piece, coordinates);

        Optional<Piece> removedPiece = board.removePieceAt(coordinates);
        Assertions.assertEquals(removedPiece.get(), piece);

        Optional<Piece> pieceOnBoard = board.getPieceAt(coordinates);
        Assertions.assertFalse(pieceOnBoard.isPresent());
    }

}
