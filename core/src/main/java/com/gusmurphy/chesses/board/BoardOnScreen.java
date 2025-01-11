package com.gusmurphy.chesses.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.MatchScreen;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinatesXyAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BoardOnScreen {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final Viewport viewport;
    private final Texture darkSquareTexture;
    private final Texture lightSquareTexture;
    private final float squareSize;
    private final Rectangle bounds;
    private ArrayList<BoardCoordinates> highlightedSpaces;

    static private final int BOARD_WIDTH_IN_SQUARES = 8;

    public BoardOnScreen(final ChessesGame game, float squareSize) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();
        bounds = new Rectangle();

        lightSquareTexture = new Texture("light_square.png");
        darkSquareTexture = new Texture("dark_square.png");

        this.squareSize = squareSize;

        highlightedSpaces = new ArrayList<>();
    }

    public void draw() {
        float boardWidth = boardWidth();
        float bottomLeftX = bottomLeftX();
        float bottomLeftY = bottomLeftY();
        bounds.set(bottomLeftX, bottomLeftY, boardWidth, boardWidth);

        spriteBatch.begin();

        for (int x = 0; x < BOARD_WIDTH_IN_SQUARES; x++) {
            for (int y = 0; y < BOARD_WIDTH_IN_SQUARES; y++) {
                boolean isDark = (x % 2) == (y % 2);
                Texture texture = isDark ? darkSquareTexture : lightSquareTexture;
                float xPosition = x * squareSize + bottomLeftX;
                float yPosition = y * squareSize + bottomLeftY;
                spriteBatch.draw(texture, xPosition, yPosition, squareSize, squareSize);
            }
        }

        spriteBatch.end();

        for (BoardCoordinates space : highlightedSpaces) {
            Vector2 center = getScreenPositionForCenterOf(space);
            float xPosition = center.x - squareSize / 2;
            float yPosition = center.y - squareSize / 2;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(xPosition, yPosition, squareSize, squareSize);
            shapeRenderer.end();
        }
    }

    public void addHighlightedSpaces(List<BoardCoordinates> spaces) {
        highlightedSpaces.addAll(spaces);
    }

    public void clearHighlightedSpaces() {
        highlightedSpaces.clear();
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

    public Optional<BoardCoordinates> getBoardCoordinatesOfScreenPosition(Vector2 screenPosition) {
        if (bounds.contains(screenPosition)) {
            float xWithinBoard = screenPosition.x - bottomLeftX();
            float yWithinBoard = screenPosition.y - bottomLeftY();

            int x = (int) Math.floor(xWithinBoard / squareSize);
            int y = (int) Math.floor(yWithinBoard / squareSize);

            BoardCoordinatesXyAdapter adapter = new BoardCoordinatesXyAdapter(x, y);
            return Optional.of(adapter.coordinates());
        }

        return Optional.empty();
    }

    private float boardWidth() {
        return BOARD_WIDTH_IN_SQUARES * squareSize;
    }

    private float bottomLeftX() {
        return viewport.getWorldWidth() / 2 - boardWidth() / 2;
    }

    private float bottomLeftY() {
        return viewport.getWorldHeight() / 2 - boardWidth() / 2;
    }

}
