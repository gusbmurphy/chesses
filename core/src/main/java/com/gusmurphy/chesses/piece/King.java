package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.player.PlayerColor;

public class King {

    private final PlayerColor color;

    public King() {
        color = PlayerColor.WHITE;
    }

    public King(PlayerColor color) {
        this.color = color;
    }

    public PlayerColor color() {
        return color;
    }

}
