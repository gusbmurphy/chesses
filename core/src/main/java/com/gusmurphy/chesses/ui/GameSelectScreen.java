package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.Variations;

public class GameSelectScreen extends BaseScreen {

    private final Stage stage;

    public GameSelectScreen(final ChessesGame game) {
        // TODO: Looking like some duplication that could live in the BaseScreen maybe?
        stage = new Stage();

        Skin skin = getSkin();

        TextButton regularButton = new TextButton("Regular Style", skin);
        regularButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MatchScreen(game, Variations.standard()));
            }
        });
        stage.addActor(regularButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private static Skin getSkin() {
        return new Skin(
            Gdx.files.internal("uiskin.json"),
            new TextureAtlas(
                Gdx.files.internal("uiskin.atlas")
            )
        );
    }

}
