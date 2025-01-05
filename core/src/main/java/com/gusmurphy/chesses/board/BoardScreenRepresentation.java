package com.gusmurphy.chesses.board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gusmurphy.chesses.MatchScreen;

public class BoardScreenRepresentation {

    private static Texture darkSquareTexture;
    private static Texture lightSquareTexture;
    private static SpriteBatch spriteBatch;
    private static float squareSize;
    private static int boardWidthInSquares;

    public static void draw(Texture darkSquareTexture, Texture lightSquareTexture, SpriteBatch spriteBatch, FitViewport viewport) {
        BoardScreenRepresentation.spriteBatch = spriteBatch;
        BoardScreenRepresentation.darkSquareTexture = darkSquareTexture;
        BoardScreenRepresentation.lightSquareTexture = lightSquareTexture;
        squareSize = MatchScreen.SQUARE_SIZE;
        boardWidthInSquares = MatchScreen.BOARD_WIDTH_IN_SQUARES;

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = boardWidthInSquares * squareSize;

        for (int x = 0; x < boardWidthInSquares; x++) {
            for (int y = 0; y < boardWidthInSquares; y++) {
                boolean isDark = (x % 2) == (y % 2);
                Texture texture = isDark ? BoardScreenRepresentation.darkSquareTexture : BoardScreenRepresentation.lightSquareTexture;
                float xPosition = x * squareSize + worldWidth / 2 - boardWidth / 2;
                float yPosition = y * squareSize + worldHeight / 2 - boardWidth / 2;
                BoardScreenRepresentation.spriteBatch.draw(texture, xPosition, yPosition, squareSize, squareSize);
            }
        }
    }

}
