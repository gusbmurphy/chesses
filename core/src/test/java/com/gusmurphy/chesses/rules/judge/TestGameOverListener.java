package com.gusmurphy.chesses.rules.judge;

public class TestGameOverListener implements GameOverListener {

    private GameOverEvent latestEvent;

    @Override
    public void onGameOverEvent(GameOverEvent event) {
        latestEvent = event;
    }

    public GameOverEvent getLatestEvent() {
        return latestEvent;
    }

}
