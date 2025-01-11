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
    private final Sprite kingSprite;
    private final Rectangle kingRectangle;
    private Vector2 effectivePosition;
    private boolean draggingKing = false;
    private final List<PieceSelectionListener> selectionListeners = new ArrayList<>();

    public PieceOnScreen(SpriteBatch spriteBatch, Float squareSize, Vector2 initialPosition) {
        this.spriteBatch = spriteBatch;

        Texture kingTexture = new Texture("b_king.png");
        kingSprite = new Sprite(kingTexture);
        kingSprite.setSize(squareSize, squareSize);
        kingRectangle = new Rectangle();
        effectivePosition = initialPosition;
        kingSprite.setCenter(initialPosition.x, initialPosition.y);
    }

    public void subscribeToMovement(PieceSelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void drag(Vector2 cursorPosition) {
        if (Gdx.input.justTouched()) {
            if (!draggingKing) {
                if (kingRectangle.contains(cursorPosition)) {
                    draggingKing = true;
                    selectionListeners.forEach(listener -> listener.onPieceSelected(this));
                }
            } else {
                draggingKing = false;
                selectionListeners.forEach(listener -> listener.onPieceReleased(this, cursorPosition));
                kingSprite.setCenter(effectivePosition.x, effectivePosition.y);
            }
        }

        if (draggingKing) {
            kingSprite.setPosition(
                cursorPosition.x - kingSprite.getWidth() / 2, cursorPosition.y - kingSprite.getHeight() / 2
            );
        }
    }

    public void setEffectivePosition(Vector2 position) {
        this.effectivePosition = position;
    }

    public void draw() {
        spriteBatch.begin();
        kingSprite.draw(spriteBatch);
        kingRectangle.set(kingSprite.getX(), kingSprite.getY(), kingSprite.getWidth(), kingSprite.getHeight());
        spriteBatch.end();
    }

}
