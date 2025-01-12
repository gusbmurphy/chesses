package com.gusmurphy.chesses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gusmurphy.chesses.board.*;
import com.gusmurphy.chesses.piece.DefaultPieces;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.player.PlayerColor;

import java.util.Arrays;
import java.util.List;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.A4;
import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.H1;

public class MatchScreen extends BaseScreen {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final FitViewport viewport;

    private final BoardOnScreen boardOnScreen;

    public MatchScreen(final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();

        List<Piece> pieces = Arrays.asList(
            DefaultPieces.king(PlayerColor.BLACK, A4),
            DefaultPieces.king(PlayerColor.WHITE, H1)
        );

        BoardState boardState = new BoardState();
        pieces.forEach(boardState::place);

        boardOnScreen = new BoardOnScreen(boardState, game);
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
    }

}
