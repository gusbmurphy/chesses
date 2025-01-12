package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.DefaultPieces;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.player.PlayerColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;

public class BoardStateTests {

    @Test
    void aPieceCanBeAddedToTheBoard() {
        Piece piece = DefaultPieces.king(PlayerColor.WHITE, B5);
        BoardState boardState = new BoardState();

        boardState.place(piece);

        Optional<Piece> pieceOnBoard = boardState.getPieceAt(B5);

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
        BoardCoordinates coordinates = B5;
        BoardState boardState = new BoardState();
        Piece piece = DefaultPieces.king(PlayerColor.WHITE, coordinates);

        boardState.place(piece);

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

    @Test
    void allPiecesOnTheBoardCanBeRetrieved() {
        BoardState board = new BoardState();

        Piece pieceA = DefaultPieces.king(PlayerColor.WHITE, A3);
        Piece pieceB = DefaultPieces.king(PlayerColor.WHITE, H4);
        Piece pieceC = DefaultPieces.king(PlayerColor.WHITE, H8);
        board.place(pieceA);
        board.place(pieceB);
        board.place(pieceC);

        List<Piece> result = board.getAllPieces();

        Assertions.assertEquals(result.size(), 3);
    }

}
