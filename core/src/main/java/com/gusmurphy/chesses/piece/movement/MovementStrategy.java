package com.gusmurphy.chesses.piece.movement;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.List;

public interface MovementStrategy {

    List<Move> possibleMovesFrom(BoardCoordinates position);

}
