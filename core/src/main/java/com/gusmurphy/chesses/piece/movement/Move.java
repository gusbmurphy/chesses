package com.gusmurphy.chesses.piece.movement;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Optional;

public interface Move {

    BoardCoordinates spot();
    Optional<Move> next();
    Optional<Piece> takes();

}
