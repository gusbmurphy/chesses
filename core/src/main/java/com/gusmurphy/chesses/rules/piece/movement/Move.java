package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public abstract class Move {

    public abstract BoardCoordinates spot();
    public abstract Optional<Move> next();
    public abstract Optional<Piece> takes();

}
