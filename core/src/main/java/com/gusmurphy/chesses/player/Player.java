package com.gusmurphy.chesses.player;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.Board;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Optional;

public class Player {

    private final PlayerColor color;

    public Player(PlayerColor color) {
        this.color = color;
    }

    public Optional<Piece> selectPieceToMove(Board board, BoardCoordinates spot) {
        Optional<Piece> pieceAtSpot = board.getPieceAt(spot);

        if (pieceAtSpot.isPresent() && pieceAtSpot.get().color() == color) {
            return pieceAtSpot;
        }

        return Optional.empty();
    }

}
