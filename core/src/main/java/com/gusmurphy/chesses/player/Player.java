package com.gusmurphy.chesses.player;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Optional;

public class Player {

    private final PlayerColor color;

    public Player(PlayerColor color) {
        this.color = color;
    }

    public Optional<Piece> selectPieceToMove(BoardState board, BoardCoordinates spot) {
        Optional<Piece> pieceAtSpot = board.getPieceAt(spot);
        if (pieceAtSpot.get().color() == color) {
            return Optional.of(pieceAtSpot.get());
        }
        return Optional.empty();
    }

}
