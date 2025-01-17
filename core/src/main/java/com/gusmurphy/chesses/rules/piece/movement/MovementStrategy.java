package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;

import java.util.List;

public interface MovementStrategy {

    List<Move> possibleMovesFrom(BoardCoordinates position);

}
