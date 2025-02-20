package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.piece.movement.strategy.MovementStrategy;

public interface MovementStrategyProvider {

    MovementStrategy movementStrategyFor(PieceType type);

}
