package com.gusmurphy.chesses.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PieceOnScreen {

    private final SpriteBatch spriteBatch;
    private final Sprite sprite;
    private final Rectangle bounds;
    private Vector2 effectivePosition;
    private boolean isDragged = false;
    private final List<PieceSelectionListener> selectionListeners = new ArrayList<>();

    public PieceOnScreen(SpriteBatch spriteBatch, Float squareSize, Vector2 initialPosition) {
        this.spriteBatch = spriteBatch;

        Texture kingTexture = new Texture("b_king.png");
        sprite = new Sprite(kingTexture);
        sprite.setSize(squareSize, squareSize);
        bounds = new Rectangle();
        effectivePosition = initialPosition;
        sprite.setCenter(initialPosition.x, initialPosition.y);
    }

    public void subscribeToMovement(PieceSelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void drag(Vector2 cursorPosition) {
        if (Gdx.input.justTouched()) {
            updateDragStatusBasedOn(cursorPosition);
        }

        if (isDragged) {
            updateSpritePosition(cursorPosition);
        }
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

    public void setEffectivePosition(Vector2 position) {
        this.effectivePosition = position;
    }

    public void draw() {
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        spriteBatch.end();
    }

}
