package com.gusmurphy.chesses.rules.piece.movement.strategy;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.List;

public interface MovementStrategy {

    List<Move> possibleMovesFrom(Coordinates position);

}
