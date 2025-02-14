package com.gusmurphy.chesses.rules;

public enum PlayerColor {
    WHITE, BLACK;

    public PlayerColor opposite() {
        if (this == BLACK) {
            return WHITE;
        }

        return BLACK;
    }
}
