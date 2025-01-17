package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.piece.movement.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.NullMovementStrategy;
import com.gusmurphy.chesses.rules.PlayerColor;

// TODO: This can't be like this...
public class PieceColorAndMovement {

    private PlayerColor color;
    private MovementStrategy movementStrategy;

    public PieceColorAndMovement() {
        color = PlayerColor.WHITE;
        movementStrategy = new NullMovementStrategy();
    }

    public PieceColorAndMovement(PlayerColor color) {
        this();
        this.color = color;
    }

    public PieceColorAndMovement(PlayerColor color, MovementStrategy movementStrategy) {
        this(color);
        this.movementStrategy = movementStrategy;
    }

    public MovementStrategy movementStrategy() {
        return movementStrategy;
    }

    public PlayerColor color() {
        return color;
    }

}
