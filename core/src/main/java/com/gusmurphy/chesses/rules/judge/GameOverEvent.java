package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;

public class GameOverEvent {

    private final GameOverEventType type;
    private final PlayerColor relevantColor;

    public GameOverEvent(GameOverEventType type, PlayerColor relevantColor) {
        this.type = type;
        this.relevantColor = relevantColor;
    }

    public GameOverEventType type() {
        return type;
    }

    public PlayerColor relevantColor() {
        return relevantColor;
    }

}
