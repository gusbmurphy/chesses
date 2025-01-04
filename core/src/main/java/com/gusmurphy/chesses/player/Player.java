package com.gusmurphy.chesses.player;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Optional;

public class Player {

    public Player(PlayerColor color) {
    }

    public Optional<Piece> selectPieceToMove(BoardState board, BoardCoordinates spot) {
        return Optional.empty();
    }

}
