package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;

import java.util.Optional;

public class TestRuleEventListener implements RuleEventListener {

    private PlayerColor latestTurnColor;

    @Override
    public void onTurnChange(PlayerColor newTurnColor) {
        latestTurnColor = newTurnColor;
    }

    public Optional<PlayerColor> getLatestTurnColor() {
        return Optional.ofNullable(latestTurnColor);
    }

}
