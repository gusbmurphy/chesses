package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.King;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;

public class BoardStateTests {

    @Test
    void aPieceCanBeAddedToTheBoard() {
        King piece = new King();
        BoardCoordinates coordinates = B5;
        BoardState boardState = new BoardState();

        boardState.placePieceAt(piece, coordinates);

        Optional<King> pieceOnBoard = boardState.getPieceAt(coordinates);

        Assertions.assertEquals(pieceOnBoard.get(), piece);
    }

    @Test
    void anEmptyOptionalIsReturnedForAnEmptyPosition() {
        BoardState boardState = new BoardState();

        Optional<King> result = boardState.getPieceAt(B5);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void aPieceCanBeRemoved() {
        King piece = new King();
        BoardCoordinates coordinates = B5;
        BoardState boardState = new BoardState();

        boardState.placePieceAt(piece, coordinates);

        Optional<King> removedPiece = boardState.removePieceAt(coordinates);
        Assertions.assertEquals(removedPiece.get(), piece);

        Optional<King> pieceOnBoard = boardState.getPieceAt(coordinates);
        Assertions.assertFalse(pieceOnBoard.isPresent());
    }

    @Test
    void ifThereIsNoPieceToBeRemovedAnEmptyIsReturned() {
        BoardState boardState = new BoardState();

        Optional<King> result = boardState.removePieceAt(B5);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void allPiecesOnTheBoardCanBeRetrieved() {
        BoardState board = new BoardState();

        King pieceA = new King();
        King pieceB = new King();
        King pieceC = new King();
        board.placePieceAt(pieceA, A3);
        board.placePieceAt(pieceB, H4);
        board.placePieceAt(pieceC, H8);

        List<PieceAndCoordinates> result = board.getAllPieces();

        Assertions.assertEquals(result.size(), 3);
    }

}
