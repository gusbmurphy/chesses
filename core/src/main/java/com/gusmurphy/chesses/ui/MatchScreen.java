package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.StartingBoards;
import com.gusmurphy.chesses.rules.judge.GameOverEvent;
import com.gusmurphy.chesses.rules.judge.GameOverEventType;
import com.gusmurphy.chesses.rules.judge.GameOverListener;
import com.gusmurphy.chesses.ui.pawntransform.PawnTransformRequestMenu;

public class MatchScreen extends BaseScreen implements GameOverListener {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final FitViewport viewport;

    private final BoardOnScreen boardOnScreen;

    private boolean checkmate = false;

    private final Stage stage;
    private final Label checkmateLabel;

    public MatchScreen(final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();

        boardOnScreen = new BoardOnScreen(StartingBoards.regular(), game);
        boardOnScreen.getJudge().subscribeToGameOver(this);

        Skin skin = getSkin();

        stage = new Stage();
        checkmateLabel = new Label("Checkmate.", skin);
        checkmateLabel.setVisible(false);
        stage.addActor(checkmateLabel);

        setupPawnTransformMenus(skin);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        boardOnScreen.render();
        drawScreen();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void onGameOverEvent(GameOverEvent event) {
        if (event.type() == GameOverEventType.CHECKMATE) {
            checkmate = true;
        }
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
        boardOnScreen.getJudge().subscribeToPawnTransform(whitePawnTransformMenu); // TODO: Why do we have to get the judge?
        boardOnScreen.getJudge().subscribeToPawnTransform(blackPawnTransformMenu);
        stage.addActor(whitePawnTransformMenu);
        stage.addActor(blackPawnTransformMenu);
    }

    private void drawScreen() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        boardOnScreen.draw();

        if (checkmate) {
            checkmateLabel.setVisible(true);
        }
    }

}
