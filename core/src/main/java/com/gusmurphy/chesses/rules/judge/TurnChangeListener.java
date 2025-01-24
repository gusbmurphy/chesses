package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;

public interface TurnChangeListener {
    void onTurnChange(PlayerColor newTurnColor);
}
