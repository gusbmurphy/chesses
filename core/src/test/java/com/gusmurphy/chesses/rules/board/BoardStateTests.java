package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.PieceFactory;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.PlayerColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;

public class BoardStateTests {

    private final PieceFactory pieceFactory = new PieceFactory();

    @Test
    void aPieceCanBeAddedToTheBoard() {
        Piece piece = pieceFactory.rook(PlayerColor.WHITE, B5);
        BoardState boardState = new BoardState();

        boardState.place(piece);

        Optional<Piece> pieceOnBoard = boardState.getStateAt(B5).occupyingPiece();

        Assertions.assertEquals(pieceOnBoard.get(), piece);
    }

    @Test
    void anEmptyOptionalIsReturnedForAnEmptyPosition() {
        BoardState boardState = new BoardState();

        Optional<Piece> result = boardState.getStateAt(B5).occupyingPiece();

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void aPieceCanBeRemoved() {
        Coordinates coordinates = B5;
        BoardState boardState = new BoardState();
        Piece piece = pieceFactory.rook(PlayerColor.WHITE, coordinates);

        boardState.place(piece);

        Optional<Piece> removedPiece = boardState.removePieceAt(coordinates);
        Assertions.assertEquals(removedPiece.get(), piece);

        Optional<Piece> pieceOnBoard = boardState.getStateAt(coordinates).occupyingPiece();
        Assertions.assertFalse(pieceOnBoard.isPresent());
    }

    @Test
    void ifThereIsNoPieceToBeRemovedAnEmptyIsReturned() {
        BoardState boardState = new BoardState();

        Optional<Piece> result = boardState.removePieceAt(B5);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void allPiecesOnTheBoardCanBeRetrieved() {
        BoardState board = new BoardState();

        Piece pieceA = pieceFactory.rook(PlayerColor.WHITE, A3);
        Piece pieceB = pieceFactory.rook(PlayerColor.WHITE, H4);
        Piece pieceC = pieceFactory.rook(PlayerColor.WHITE, H8);
        board.place(pieceA);
        board.place(pieceB);
        board.place(pieceC);

        List<Piece> result = board.getAllPieces();

        Assertions.assertEquals(result.size(), 3);
    }

    @Test
    void creatingABoardFromAnotherOneCreatesDuplicatesOfThePieces() {
        Piece originalPiece = pieceFactory.rook(PlayerColor.BLACK, A2);
        BoardState original = new BoardState(originalPiece);
        BoardState copy = new BoardState(original);

        Assertions.assertEquals(1, copy.getAllPieces().size());
        Assertions.assertNotEquals(originalPiece, copy.getAllPieces().get(0));
    }

}
