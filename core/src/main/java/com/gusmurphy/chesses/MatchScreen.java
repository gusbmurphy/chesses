package com.gusmurphy.chesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gusmurphy.chesses.board.*;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.judge.Judge;
import com.gusmurphy.chesses.piece.*;
import com.gusmurphy.chesses.player.PlayerColor;

import java.util.List;
import java.util.Optional;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;

public class MatchScreen extends BaseScreen implements PieceSelectionListener, BoardStateEventListener {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final FitViewport viewport;

    private final Vector2 cursorPosition;

    private final BoardOnScreen boardOnScreen;
    private final PieceOnScreen kingOnScreen;

    private final Judge judge;
    private final Piece king;

    public MatchScreen(final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();

        cursorPosition = new Vector2();

        boardOnScreen = new BoardOnScreen(game);
        kingOnScreen = new PieceOnScreen(game.getSpriteBatch(), boardOnScreen.getScreenPositionForCenterOf(A4));

        king = new Piece(DefaultPieces.king(PlayerColor.BLACK), A4);

        BoardState boardState = new BoardState();
        boardState.place(king);
        BoardStateEventManager boardStateEventManager = new BoardStateEventManager(boardState);
        boardStateEventManager.subscribe(this, BoardStateEvent.PIECE_MOVED);

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
                king.moveTo(releaseSpot.get());
                boardOnScreen.clearHighlightedSpaces();
            }
        }
    }

    @Override
    public void onBoardStateEvent(BoardStateEvent event, Piece piece) {
        if (event == BoardStateEvent.PIECE_MOVED) {
            kingOnScreen.setEffectivePosition(boardOnScreen.getScreenPositionForCenterOf(piece.getCoordinates()));
        }
    }

}
