package com.gusmurphy.chesses;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ChessesGame extends Game {

    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        this.setScreen(new MatchScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    public FitViewport getViewport() {
        return viewport;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

}
