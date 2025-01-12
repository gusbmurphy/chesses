package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.Optional;

public interface PossibleMove {

    BoardCoordinates spot();
    Optional<Direction> continuedDirection();
    int continuedDistance();

}
