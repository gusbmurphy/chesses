package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public interface Move {

    BoardCoordinates spot();
    Optional<Move> next();
    Optional<Piece> takes();

}
