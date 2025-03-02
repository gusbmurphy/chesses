package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.StartingBoards;
import com.gusmurphy.chesses.ui.pawntransform.PawnTransformRequestMenu;

public class MatchScreen extends BaseScreen {

    private final ChessesGame game;
    private final BoardRepresentation boardRepresentation;
    private final Stage stage;

    public MatchScreen(final ChessesGame game) {
        this.game = game;
        boardRepresentation = new BoardRepresentation(StartingBoards.regular(), game);

        Skin skin = getSkin();
        CheckmateIndicator checkmateIndicator = new CheckmateIndicator(skin);
        boardRepresentation.getJudge().subscribeToGameOver(checkmateIndicator);

        stage = new Stage();
        stage.addActor(checkmateIndicator);

        setupPawnTransformMenus(skin);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        boardRepresentation.render();
        drawScreen();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    private static Skin getSkin() {
        return new Skin(
            Gdx.files.internal("uiskin.json"),
            new TextureAtlas(
                Gdx.files.internal("uiskin.atlas")
            )
        );
    }

    private void setupPawnTransformMenus(Skin skin) {
        PawnTransformRequestMenu whitePawnTransformMenu = new PawnTransformRequestMenu(skin, PlayerColor.WHITE);
        PawnTransformRequestMenu blackPawnTransformMenu = new PawnTransformRequestMenu(skin, PlayerColor.BLACK);
        boardRepresentation.getJudge().subscribeToPawnTransform(whitePawnTransformMenu); // TODO: Why do we have to get the judge?
        boardRepresentation.getJudge().subscribeToPawnTransform(blackPawnTransformMenu);
        stage.addActor(whitePawnTransformMenu);
        stage.addActor(blackPawnTransformMenu);
    }

    private void drawScreen() {
        ScreenUtils.clear(Color.WHITE);
        game.getViewport().apply();
        game.getSpriteBatch().setProjectionMatrix(game.getViewport().getCamera().combined);
        game.getShapeRenderer().setProjectionMatrix(game.getViewport().getCamera().combined);

        boardRepresentation.draw();
    }

}
