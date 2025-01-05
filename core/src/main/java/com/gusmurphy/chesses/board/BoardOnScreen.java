package com.gusmurphy.chesses.board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gusmurphy.chesses.MatchScreen;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinatesXyAdapter;

public class BoardOnScreen {

    private final SpriteBatch spriteBatch;
    private final Viewport viewport;
    private final Texture darkSquareTexture;
    private final Texture lightSquareTexture;
    private final float squareSize;

    static private final int BOARD_WIDTH_IN_SQUARES = 8;

    public BoardOnScreen(final MatchScreen screen, float squareSize) {
        this.spriteBatch = screen.getSpriteBatch();
        this.viewport = screen.getViewport();

        lightSquareTexture = new Texture("light_square.png");
        darkSquareTexture = new Texture("dark_square.png");

        this.squareSize = squareSize;
    }

    public void draw() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = BOARD_WIDTH_IN_SQUARES * squareSize;

        for (int x = 0; x < BOARD_WIDTH_IN_SQUARES; x++) {
            for (int y = 0; y < BOARD_WIDTH_IN_SQUARES; y++) {
                boolean isDark = (x % 2) == (y % 2);
                Texture texture = isDark ? darkSquareTexture : lightSquareTexture;
                float xPosition = x * squareSize + worldWidth / 2 - boardWidth / 2;
                float yPosition = y * squareSize + worldHeight / 2 - boardWidth / 2;
                spriteBatch.draw(texture, xPosition, yPosition, squareSize, squareSize);
            }
        }
    }

    public Vector2 getScreenPositionForCenterOf(BoardCoordinates coordinates) {
        BoardCoordinatesXyAdapter xyAdapter = new BoardCoordinatesXyAdapter(coordinates);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = BOARD_WIDTH_IN_SQUARES * squareSize;

        float x = xyAdapter.x() * squareSize + squareSize / 2 + worldWidth / 2 - boardWidth / 2;
        float y = xyAdapter.y() * squareSize + squareSize / 2 + worldHeight / 2 - boardWidth / 2;
        return new Vector2(x, y);
    }

}
