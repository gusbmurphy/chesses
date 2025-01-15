package com.gusmurphy.chesses.piece.movement;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.Piece;

import java.util.Optional;

public abstract class Move {

    public abstract BoardCoordinates spot();
    public abstract Optional<Move> next();
    public abstract Optional<Piece> takes();

}
