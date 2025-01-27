package com.gusmurphy.chesses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gusmurphy.chesses.rules.board.BoardOnScreen;
import com.gusmurphy.chesses.rules.board.StartingBoards;
import com.gusmurphy.chesses.rules.judge.GameOverEvent;
import com.gusmurphy.chesses.rules.judge.GameOverEventType;
import com.gusmurphy.chesses.rules.judge.GameOverListener;

public class MatchScreen extends BaseScreen implements GameOverListener {

    private final SpriteBatch spriteBatch;
    private final BitmapFont font;
    private final ShapeRenderer shapeRenderer;
    private final FitViewport viewport;
    private final ScreenViewport fontViewport;

    private final BoardOnScreen boardOnScreen;

    private boolean checkmate = false;

    public MatchScreen(final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();
        fontViewport = new ScreenViewport();
        font = new BitmapFont();

        boardOnScreen = new BoardOnScreen(StartingBoards.rookRoller(), game);
        boardOnScreen.getJudge().subscribeToGameOver(this);
    }

    @Override
    public void render(float delta) {
        boardOnScreen.render();
        drawScreen();
    }

    private void drawScreen() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        boardOnScreen.draw();

        if (checkmate) {
            fontViewport.apply();
            spriteBatch.setProjectionMatrix(fontViewport.getCamera().combined);
            spriteBatch.begin();
            font.setColor(Color.BLACK);
            font.draw(spriteBatch, "Checkmate!", 0, 0);
            spriteBatch.end();
        }
    }

    @Override
    public void onGameOverEvent(GameOverEvent event) {
        if (event.type() == GameOverEventType.CHECKMATE) {
            checkmate = true;
        }
    }
}
