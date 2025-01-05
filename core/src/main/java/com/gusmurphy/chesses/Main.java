package com.gusmurphy.chesses;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private Texture lightSquareTexture;
    private Texture darkSquareTexture;
    private FitViewport viewport;
    static private final int BOARD_SIZE = 8;
    static private final float SQUARE_SIZE = 0.5f;

    @Override
    public void create() {
        lightSquareTexture = new Texture("light_square.png");
        darkSquareTexture = new Texture("dark_square.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                boolean isDark = (x % 2) == (y % 2);
                Texture texture = isDark ? darkSquareTexture : lightSquareTexture;
                float xPosition = x * SQUARE_SIZE;
                float yPosition = y * SQUARE_SIZE;
                spriteBatch.draw(texture, xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

}
