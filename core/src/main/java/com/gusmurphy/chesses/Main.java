package com.gusmurphy.chesses;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter {

    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private Texture lightSquareTexture;
    private Texture darkSquareTexture;
    private Sprite kingSprite;
    private Rectangle kingRectangle;
    private boolean draggingKing = false;
    private Vector2 cursorPosition;

    static private final int BOARD_WIDTH_IN_SQUARES = 8;
    static private final float SQUARE_SIZE = 0.5f;

    @Override
    public void create() {
        lightSquareTexture = new Texture("light_square.png");
        darkSquareTexture = new Texture("dark_square.png");

        Texture kingTexture = new Texture("b_king.png");
        kingSprite = new Sprite(kingTexture);
        kingSprite.setSize(SQUARE_SIZE, SQUARE_SIZE);
        kingRectangle = new Rectangle();

        cursorPosition = new Vector2();

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
    }

    @Override
    public void render() {
        cursorPosition.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPosition);

        if (Gdx.input.justTouched()) {
            if (!draggingKing) {
                if (kingRectangle.contains(cursorPosition)) {
                    draggingKing = true;
                }
            } else {
                draggingKing = false;
            }
        }

        if (draggingKing) {
            kingSprite.setPosition(
                cursorPosition.x - kingSprite.getWidth() / 2, cursorPosition.y - kingSprite.getHeight() / 2
            );
        }

        drawScreen();
    }

    private void drawScreen() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        drawBoard();
        kingSprite.draw(spriteBatch);
        kingRectangle.set(kingSprite.getX(), kingSprite.getY(), kingSprite.getWidth(), kingSprite.getHeight());

        spriteBatch.end();
    }

    private void drawBoard() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = BOARD_WIDTH_IN_SQUARES * SQUARE_SIZE;

        for (int x = 0; x < BOARD_WIDTH_IN_SQUARES; x++) {
            for (int y = 0; y < BOARD_WIDTH_IN_SQUARES; y++) {
                boolean isDark = (x % 2) == (y % 2);
                Texture texture = isDark ? darkSquareTexture : lightSquareTexture;
                float xPosition = x * SQUARE_SIZE + worldWidth / 2 - boardWidth / 2;
                float yPosition = y * SQUARE_SIZE + worldHeight / 2 - boardWidth / 2;
                spriteBatch.draw(texture, xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

}
