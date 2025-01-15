package com.gusmurphy.chesses.piece.movement;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.Optional;

public interface PossibleMove {

    BoardCoordinates spot();
    Optional<PossibleMove> next();

}
