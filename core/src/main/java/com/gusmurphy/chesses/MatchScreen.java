package com.gusmurphy.chesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gusmurphy.chesses.board.BoardOnScreen;
import com.gusmurphy.chesses.piece.KingOnScreen;

public class MatchScreen implements Screen {

    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;

    private final Vector2 cursorPosition;

    private final BoardOnScreen board;
    private final KingOnScreen king;

    static final float SQUARE_SIZE = 0.5f;

    public MatchScreen(final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        viewport = game.getViewport();

        cursorPosition = new Vector2();

        king = new KingOnScreen(spriteBatch, SQUARE_SIZE);
        board = new BoardOnScreen(this, SQUARE_SIZE);
    }

    @Override
    public void render(float delta) {
        cursorPosition.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPosition);

        king.drag(cursorPosition);

        drawScreen();
    }

    private void drawScreen() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        board.draw();
        king.draw();

        spriteBatch.end();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public FitViewport getViewport() {
        return viewport;
    }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }

}
