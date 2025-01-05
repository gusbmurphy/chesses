package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.List;

public interface MovementStrategy {

    List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position);

}
