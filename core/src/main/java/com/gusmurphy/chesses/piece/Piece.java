package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.player.PlayerColor;

public class Piece {

    private PlayerColor color;
    private MovementStrategy movementStrategy;

    public Piece() {
        color = PlayerColor.WHITE;
        movementStrategy = new NullMovementStrategy();
    }

    public Piece(PlayerColor color) {
        this();
        this.color = color;
    }

    public Piece(PlayerColor color, MovementStrategy movementStrategy) {
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
