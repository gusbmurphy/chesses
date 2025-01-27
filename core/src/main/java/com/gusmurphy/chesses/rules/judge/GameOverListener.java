package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;

public interface GameOverListener {
    void onGameOverEvent(GameOverEvent event);
}
