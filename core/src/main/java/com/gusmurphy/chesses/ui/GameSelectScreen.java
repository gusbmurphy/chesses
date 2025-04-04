package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.GameVariation;

public class GameSelectScreen extends BaseScreen {

    public GameSelectScreen(final ChessesGame game) {
        super(game);

        TextButton regularButton = new VariantSelectButton(game, skin, GameVariation.standard());
        regularButton.setX(10);

        TextButton singlePlayerButton = new VariantSelectButton(game, skin, GameVariation.singlePlayer());
        singlePlayerButton.setX(200);

        TextButton moveEveryPieceButton = new VariantSelectButton(game, skin, GameVariation.moveEveryPiece());
        moveEveryPieceButton.setX(300);

        TextButton oopsAllSomethingButton = new VariantSelectButton(game, skin, GameVariation.oopsAllSomething());
        oopsAllSomethingButton.setX(400);

        stage.addActor(regularButton);
        stage.addActor(singlePlayerButton);
        stage.addActor(moveEveryPieceButton);
        stage.addActor(oopsAllSomethingButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

}
