package com.gusmurphy.chesses.player;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.King;
import com.gusmurphy.chesses.piece.Piece;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.gusmurphy.chesses.board.BoardCoordinates.*;
import static com.gusmurphy.chesses.player.PlayerColor.BLACK;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTests {

    @Test
    void aPlayerCanOnlyMovePiecesOfTheirColor() {
        Player player = new Player(BLACK);
        BoardState board = new BoardState();
        Piece king = new King(BLACK);
        board.placePieceAt(king, B5);

        Optional<Piece> pieceToMove = player.selectPieceToMove(board, B5);

        assertFalse(pieceToMove.isPresent());
    }

}
