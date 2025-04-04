package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.GameVariation;

import java.util.ArrayList;
import java.util.List;

public class GameSelectScreen extends BaseScreen {

    public GameSelectScreen(final ChessesGame game) {
        super(game);

        List<TextButton> buttons = new ArrayList<>();
        buttons.add(new VariantSelectButton(game, skin, GameVariation.standard()));
        buttons.add(new VariantSelectButton(game, skin, GameVariation.singlePlayer()));
        buttons.add(new VariantSelectButton(game, skin, GameVariation.moveEveryPiece()));
        buttons.add(new VariantSelectButton(game, skin, GameVariation.oopsAllSomething()));

        Table table = new Table();
        table.setFillParent(true);
        buttons.forEach(table::add);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

}
