package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.Variation;
import com.gusmurphy.chesses.ui.board.BoardRepresentation;
import com.gusmurphy.chesses.ui.pawntransform.PawnTransformRequestMenu;

public class MatchScreen extends BaseScreen {

    private final ChessesGame game;
    private final BoardRepresentation boardRepresentation;
    private final Stage stage;

    public MatchScreen(final ChessesGame game, Variation variation) {
        stage = new Stage();
        this.game = game;
        boardRepresentation = new BoardRepresentation(variation, game);

        Skin skin = getSkin();
        setupCurrentTurnIndicator(skin);
        setupCheckmarkIndicator(skin);
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

    private void setupCurrentTurnIndicator(Skin skin) {
        CurrentTurnIndicator turnIndicator = new CurrentTurnIndicator(skin);
        turnIndicator.setPosition(10, 50);
        boardRepresentation.getJudge().subscribeToTurnChange(turnIndicator);
        stage.addActor(turnIndicator);
    }

    private void setupCheckmarkIndicator(Skin skin) {
        CheckmateIndicator checkmateIndicator = new CheckmateIndicator(skin);
        boardRepresentation.getJudge().subscribeToGameOver(checkmateIndicator);

        stage.addActor(checkmateIndicator);
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
