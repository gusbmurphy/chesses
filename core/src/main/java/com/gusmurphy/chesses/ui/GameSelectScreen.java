package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.GameVariation;

public class GameSelectScreen extends BaseScreen {

    private final Stage stage;

    public GameSelectScreen(final ChessesGame game) {
        // TODO: Looking like some duplication that could live in the BaseScreen maybe?
        stage = new Stage();

        Skin skin = getSkin();

        TextButton regularButton = new VariantSelectButton(game, skin, GameVariation.standard());
        regularButton.setX(10);

        TextButton singlePlayerButton = new VariantSelectButton(game, skin, GameVariation.singlePlayer());
        singlePlayerButton.setX(200);

        stage.addActor(regularButton);
        stage.addActor(singlePlayerButton);
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
