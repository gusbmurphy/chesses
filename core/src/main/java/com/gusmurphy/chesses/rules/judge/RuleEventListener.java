package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;

public interface RuleEventListener {
    void onTurnChange(PlayerColor newTurnColor);
}
