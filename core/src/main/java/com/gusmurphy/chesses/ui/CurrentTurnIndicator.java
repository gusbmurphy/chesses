package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.judge.TurnChangeListener;

public class CurrentTurnIndicator extends Label implements TurnChangeListener {

    public CurrentTurnIndicator(Skin skin) {
        super("", skin);
    }

    @Override
    public void onTurnChange(PlayerColor newTurnColor) {
        setText(capitalize(newTurnColor.toString()) + " to move.");
    }

    private static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

}
