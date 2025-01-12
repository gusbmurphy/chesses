package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.List;

public interface MovementStrategy {

    List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position);

}
