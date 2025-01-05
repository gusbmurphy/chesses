package com.gusmurphy.chesses.board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gusmurphy.chesses.MatchScreen;

public class BoardScreenRepresentation {

    public static void draw(Texture darkSquareTexture, Texture lightSquareTexture, SpriteBatch spriteBatch, FitViewport viewport) {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = MatchScreen.BOARD_WIDTH_IN_SQUARES * MatchScreen.SQUARE_SIZE;

        for (int x = 0; x < MatchScreen.BOARD_WIDTH_IN_SQUARES; x++) {
            for (int y = 0; y < MatchScreen.BOARD_WIDTH_IN_SQUARES; y++) {
                boolean isDark = (x % 2) == (y % 2);
                Texture texture = isDark ? darkSquareTexture : lightSquareTexture;
                float xPosition = x * MatchScreen.SQUARE_SIZE + worldWidth / 2 - boardWidth / 2;
                float yPosition = y * MatchScreen.SQUARE_SIZE + worldHeight / 2 - boardWidth / 2;
                spriteBatch.draw(texture, xPosition, yPosition, MatchScreen.SQUARE_SIZE, MatchScreen.SQUARE_SIZE);
            }
        }
    }

}
