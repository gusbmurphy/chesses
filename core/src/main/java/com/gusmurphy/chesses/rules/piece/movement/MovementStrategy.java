package com.gusmurphy.chesses.rules.piece.movement;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;

import java.util.List;

public interface MovementStrategy {

    List<Move> possibleMovesFrom(Coordinates position);

}
