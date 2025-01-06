package com.gusmurphy.chesses.player;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.King;

import java.util.Optional;

public class Player {

    private final PlayerColor color;

    public Player(PlayerColor color) {
        this.color = color;
    }

    public Optional<King> selectPieceToMove(BoardState board, BoardCoordinates spot) {
        Optional<King> pieceAtSpot = board.getPieceAt(spot);

        if (pieceAtSpot.isPresent() && pieceAtSpot.get().color() == color) {
            return pieceAtSpot;
        }

        return Optional.empty();
    }

}
