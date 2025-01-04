package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.King;
import com.gusmurphy.chesses.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.gusmurphy.chesses.board.BoardCoordinates.*;

public class BoardStateTests {

    @Test
    void aPieceCanBeAddedToTheBoard() {
        Piece piece = new King();
        BoardCoordinates coordinates = B5;
        BoardState boardState = new BoardState();

        boardState.placePieceAt(piece, coordinates);

        Optional<Piece> pieceOnBoard = boardState.getPieceAt(coordinates);

        Assertions.assertEquals(pieceOnBoard.get(), piece);
    }

    @Test
    void anEmptyOptionalIsReturnedForAnEmptyPosition() {
        BoardState boardState = new BoardState();

        Optional<Piece> result = boardState.getPieceAt(B5);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void aPieceCanBeRemoved() {
        Piece piece = new King();
        BoardCoordinates coordinates = B5;
        BoardState boardState = new BoardState();

        boardState.placePieceAt(piece, coordinates);

        Optional<Piece> removedPiece = boardState.removePieceAt(coordinates);
        Assertions.assertEquals(removedPiece.get(), piece);

        Optional<Piece> pieceOnBoard = boardState.getPieceAt(coordinates);
        Assertions.assertFalse(pieceOnBoard.isPresent());
    }

    @Test
    void ifThereIsNoPieceToBeRemovedAnEmptyIsReturned() {
        BoardState boardState = new BoardState();

        Optional<Piece> result = boardState.removePieceAt(B5);

        Assertions.assertFalse(result.isPresent());
    }

}
