package com.gusmurphy.chesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gusmurphy.chesses.board.BoardOnScreen;
import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.judge.Judge;
import com.gusmurphy.chesses.piece.*;
import com.gusmurphy.chesses.player.PlayerColor;

import java.util.List;
import java.util.Optional;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;

public class MatchScreen implements Screen, PieceSelectionListener {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final FitViewport viewport;

    private final Vector2 cursorPosition;

    private final BoardOnScreen boardOnScreen;
    private final PieceOnScreen kingOnScreen;

    private final BoardState boardState;
    private final Judge judge;
    private final Piece king;

    static final float SQUARE_SIZE = 0.5f;

    public MatchScreen(final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();

        cursorPosition = new Vector2();

        boardOnScreen = new BoardOnScreen(game, SQUARE_SIZE);
        kingOnScreen = new PieceOnScreen(game.getSpriteBatch(), SQUARE_SIZE, boardOnScreen.getScreenPositionForCenterOf(A4));

        king = DefaultPieces.king(PlayerColor.BLACK);
        boardState = new BoardState();
        boardState.placePieceAt(king, A4);
        judge = new Judge(boardState);
        kingOnScreen.subscribeToMovement(this);
    }

    @Override
    public void render(float delta) {
        cursorPosition.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPosition);

        kingOnScreen.processDragging(cursorPosition);

        drawScreen();
    }

    private void drawScreen() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        boardOnScreen.draw();
        kingOnScreen.draw();
    }

    @Override
    public void onPieceSelected(PieceOnScreen piece) {
        List<BoardCoordinates> possibleMoves = judge.movesFor(king);
        boardOnScreen.addHighlightedSpaces(possibleMoves);
    }

    @Override
    public void onPieceReleased(PieceOnScreen piece, Vector2 screenPosition) {
        Optional<BoardCoordinates> releaseSpot = boardOnScreen.getBoardCoordinatesOfScreenPosition(screenPosition);

        if (releaseSpot.isPresent()) {
            if (judge.movesFor(king).contains(releaseSpot.get())) {
                boardState.removePieceAt(boardState.coordinatesForPiece(king).get());
                boardState.placePieceAt(king, releaseSpot.get());
                kingOnScreen.setEffectivePosition(boardOnScreen.getScreenPositionForCenterOf(releaseSpot.get()));
                boardOnScreen.clearHighlightedSpaces();
            }
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
