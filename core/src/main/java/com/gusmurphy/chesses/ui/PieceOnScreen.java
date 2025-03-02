package com.gusmurphy.chesses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.PieceEventListener;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceSelectionListener;
import com.gusmurphy.chesses.rules.piece.PieceSprite;

import static com.gusmurphy.chesses.ui.BoardOnScreen.*;

import java.util.ArrayList;
import java.util.List;

public class PieceOnScreen implements PieceEventListener {

    private final static float PIECE_TO_SQUARE_SIZE_RATIO = 0.8f;
    private final BoardOnScreen boardOnScreen;
    private final Piece piece;
    private final SpriteBatch spriteBatch;
    private Sprite sprite;
    private final Rectangle bounds;
    private Vector2 effectivePosition;
    private boolean isDragged = false;
    private final List<PieceSelectionListener> selectionListeners = new ArrayList<>();

    public PieceOnScreen(Piece piece, BoardOnScreen boardOnScreen) {
        this.piece = piece;
        piece.subscribeToEvents(this);
        this.spriteBatch = boardOnScreen.getSpriteBatch();

        this.boardOnScreen = boardOnScreen;

        effectivePosition = boardOnScreen.getScreenPositionForCenterOf(piece.getCoordinates());
        setSpriteFor(piece);
        bounds = new Rectangle();
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        switch (event) {
            case MOVED:
                setEffectivePosition(boardOnScreen.getScreenPositionForCenterOf(this.piece.getCoordinates()));
                break;
            case TRANSFORMED:
                setSpriteFor(piece);
                break;
        }
    }

    public void subscribeToMovement(PieceSelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void setDragStatus(boolean status) {
        isDragged = status;
    }

    public void processDragging(Vector2 cursorPosition) {
        if (Gdx.input.justTouched()) {
            checkForClick(cursorPosition);
        }

        if (isDragged) {
            updateSpritePosition(cursorPosition);
        }
    }

    public void setEffectivePosition(Vector2 position) {
        this.effectivePosition = position;
        sprite.setCenter(effectivePosition.x, effectivePosition.y);
    }

    public void draw() {
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        spriteBatch.end();
    }

    private void checkForClick(Vector2 cursorPosition) {
        if (cursorIsOnPiece(cursorPosition) && !isDragged) {
            selectionListeners.forEach(listener -> listener.onPieceSelected(piece));
        } else if (isDragged) {
            selectionListeners.forEach(listener -> listener.onPieceReleased(piece, cursorPosition));
            sprite.setCenter(effectivePosition.x, effectivePosition.y);
        }
    }

    private boolean cursorIsOnPiece(Vector2 cursorPosition) {
        return bounds.contains(cursorPosition);
    }

    private void updateSpritePosition(Vector2 cursorPosition) {
        sprite.setPosition(
            cursorPosition.x - sprite.getWidth() / 2,
            cursorPosition.y - sprite.getHeight() / 2
        );
    }

    private void setSpriteFor(Piece piece) {
        sprite = PieceSprite.spriteFor(piece);
        sprite.setSize(SQUARE_SIZE * PIECE_TO_SQUARE_SIZE_RATIO, SQUARE_SIZE * PIECE_TO_SQUARE_SIZE_RATIO);
        sprite.setCenter(effectivePosition.x, effectivePosition.y);
    }

}
