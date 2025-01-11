package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.PieceColorAndMovement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;

public class BoardStateTests {

    @Test
    void aPieceCanBeAddedToTheBoard() {
        PieceColorAndMovement pieceColorAndMovement = new PieceColorAndMovement();
        BoardCoordinates coordinates = B5;
        BoardState boardState = new BoardState();

        boardState.placePieceAt(pieceColorAndMovement, coordinates);

        Optional<PieceColorAndMovement> pieceOnBoard = boardState.getPieceAt(coordinates);

        Assertions.assertEquals(pieceOnBoard.get(), pieceColorAndMovement);
    }

    @Test
    void anEmptyOptionalIsReturnedForAnEmptyPosition() {
        BoardState boardState = new BoardState();

        Optional<PieceColorAndMovement> result = boardState.getPieceAt(B5);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void aPieceCanBeRemoved() {
        PieceColorAndMovement pieceColorAndMovement = new PieceColorAndMovement();
        BoardCoordinates coordinates = B5;
        BoardState boardState = new BoardState();

        boardState.placePieceAt(pieceColorAndMovement, coordinates);

        Optional<PieceColorAndMovement> removedPiece = boardState.removePieceAt(coordinates);
        Assertions.assertEquals(removedPiece.get(), pieceColorAndMovement);

        Optional<PieceColorAndMovement> pieceOnBoard = boardState.getPieceAt(coordinates);
        Assertions.assertFalse(pieceOnBoard.isPresent());
    }

    @Test
    void ifThereIsNoPieceToBeRemovedAnEmptyIsReturned() {
        BoardState boardState = new BoardState();

        Optional<PieceColorAndMovement> result = boardState.removePieceAt(B5);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void allPiecesOnTheBoardCanBeRetrieved() {
        BoardState board = new BoardState();

        PieceColorAndMovement pieceColorAndMovementA = new PieceColorAndMovement();
        PieceColorAndMovement pieceColorAndMovementB = new PieceColorAndMovement();
        PieceColorAndMovement pieceColorAndMovementC = new PieceColorAndMovement();
        board.placePieceAt(pieceColorAndMovementA, A3);
        board.placePieceAt(pieceColorAndMovementB, H4);
        board.placePieceAt(pieceColorAndMovementC, H8);

        List<Piece> result = board.getAllPieces();

        Assertions.assertEquals(result.size(), 3);
    }

}
