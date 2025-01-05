package com.gusmurphy.chesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MatchScreen implements Screen {

    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;

    private final Texture lightSquareTexture;
    private final Texture darkSquareTexture;
    private final Sprite kingSprite;
    private final Rectangle kingRectangle;
    private boolean draggingKing = false;
    private final Vector2 cursorPosition;

    static private final int BOARD_WIDTH_IN_SQUARES = 8;
    static private final float SQUARE_SIZE = 0.5f;

    public MatchScreen(final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        viewport = game.getViewport();

        lightSquareTexture = new Texture("light_square.png");
        darkSquareTexture = new Texture("dark_square.png");

        Texture kingTexture = new Texture("b_king.png");
        kingSprite = new Sprite(kingTexture);
        kingSprite.setSize(SQUARE_SIZE, SQUARE_SIZE);
        kingRectangle = new Rectangle();

        cursorPosition = new Vector2();
    }

    @Override
    public void render(float delta) {
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
