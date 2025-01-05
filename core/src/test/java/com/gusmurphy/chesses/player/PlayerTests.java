package com.gusmurphy.chesses.player;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.King;
import com.gusmurphy.chesses.piece.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;
import static com.gusmurphy.chesses.player.PlayerColor.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTests {

    @ParameterizedTest
    @MethodSource("playerAndPieceColors")
    void aPlayerCanOnlyMovePiecesOfTheirColor(PlayerColor playerColor, PlayerColor pieceColor, boolean expected) {
        Player player = new Player(playerColor);
        BoardState board = new BoardState();
        Piece king = new King(pieceColor);
        board.placePieceAt(king, B5);

        Optional<Piece> pieceToMove = player.selectPieceToMove(board, B5);

        assertEquals(expected, pieceToMove.isPresent());
    }

    private static Stream<Arguments> playerAndPieceColors() {
        return Stream.of(
            Arguments.of(BLACK, BLACK, true),
            Arguments.of(WHITE, WHITE, true),
            Arguments.of(BLACK, WHITE, false),
            Arguments.of(WHITE, BLACK, false)
        );
    }

    @Test
    void nothingIsReturnedIfAPlayerTriesToMoveAPieceNotThere() {
        Player player = new Player(WHITE);
        BoardState board = new BoardState();

        Optional<Piece> pieceToMove = player.selectPieceToMove(board, B5);

        assertFalse(pieceToMove.isPresent());
    }

}
