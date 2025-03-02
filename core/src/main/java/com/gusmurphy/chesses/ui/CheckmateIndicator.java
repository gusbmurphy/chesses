package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gusmurphy.chesses.rules.judge.GameOverEvent;
import com.gusmurphy.chesses.rules.judge.GameOverEventType;
import com.gusmurphy.chesses.rules.judge.GameOverListener;

public class CheckmateIndicator extends Label implements GameOverListener {

    public CheckmateIndicator(Skin skin) {
        super("Checkmate.", skin);
        setVisible(false);
    }

    @Override
    public void onGameOverEvent(GameOverEvent event) {
        if (event.type() == GameOverEventType.CHECKMATE) {
            setVisible(true);
        }
    }

}
