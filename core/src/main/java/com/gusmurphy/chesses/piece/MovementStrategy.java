package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardCoordinates;

import java.util.List;

public interface MovementStrategy {

    List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position);

}
