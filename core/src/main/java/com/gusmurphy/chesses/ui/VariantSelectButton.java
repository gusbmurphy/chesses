package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.GameVariation;

public class VariantSelectButton extends TextButton {

    public VariantSelectButton(ChessesGame game, Skin skin, GameVariation variation) {
        super(variation.displayName, skin);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MatchScreen(game, variation));
            }
        });
    }

}
