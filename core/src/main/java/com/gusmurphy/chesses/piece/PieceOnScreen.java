package com.gusmurphy.chesses.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gusmurphy.chesses.board.BoardOnScreen;

import static com.gusmurphy.chesses.board.BoardOnScreen.*;

import java.util.ArrayList;
import java.util.List;

public class PieceOnScreen {

    private final SpriteBatch spriteBatch;
    private final Sprite sprite;
    private final Rectangle bounds;
    private Vector2 effectivePosition;
    private boolean isDragged = false;
    private final List<PieceSelectionListener> selectionListeners = new ArrayList<>();

    public PieceOnScreen(Piece piece, BoardOnScreen boardOnScreen) {
        this.spriteBatch = boardOnScreen.getSpriteBatch();

        sprite = PieceSprite.spriteFor(piece);
        sprite.setSize(SQUARE_SIZE, SQUARE_SIZE);
        bounds = new Rectangle();
        effectivePosition = boardOnScreen.getScreenPositionForCenterOf(piece.getCoordinates());
        sprite.setCenter(effectivePosition.x, effectivePosition.y);
    }

    public void subscribeToMovement(PieceSelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void processDragging(Vector2 cursorPosition) {
        if (Gdx.input.justTouched()) {
            updateDragStatusBasedOn(cursorPosition);
        }

        if (isDragged) {
            updateSpritePosition(cursorPosition);
        }
    }

    public void setEffectivePosition(Vector2 position) {
        this.effectivePosition = position;
    }

    public void draw() {
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        spriteBatch.end();
    }

    private void updateDragStatusBasedOn(Vector2 cursorPosition) {
        if (!isDragged) {
            if (cursorIsOnPiece(cursorPosition)) {
                startDrag();
            }
        } else {
            endDrag(cursorPosition);
        }
    }

    private boolean cursorIsOnPiece(Vector2 cursorPosition) {
        return bounds.contains(cursorPosition);
    }

    private void startDrag() {
        isDragged = true;
        selectionListeners.forEach(listener -> listener.onPieceSelected(this));
    }

    private void endDrag(Vector2 cursorPosition) {
        isDragged = false;
        selectionListeners.forEach(listener -> listener.onPieceReleased(this, cursorPosition));
        sprite.setCenter(effectivePosition.x, effectivePosition.y);
    }

    private void updateSpritePosition(Vector2 cursorPosition) {
        sprite.setPosition(
            cursorPosition.x - sprite.getWidth() / 2,
            cursorPosition.y - sprite.getHeight() / 2
        );
    }

}
