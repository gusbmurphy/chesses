package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.player.PlayerColor;

public class Piece {

    private final PlayerColor color;

    public Piece() {
        color = PlayerColor.WHITE;
    }

    public Piece(PlayerColor color) {
        this.color = color;
    }

    public Piece(PlayerColor color, MovementStrategy movementStrategy) {
        this.color = color;
    }

    public PlayerColor color() {
        return color;
    }

}
