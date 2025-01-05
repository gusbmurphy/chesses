package com.gusmurphy.chesses.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class KingOnScreen {

    private final SpriteBatch spriteBatch;
    private final Sprite kingSprite;
    private final Rectangle kingRectangle;
    private final Vector2 effectivePosition;
    private boolean draggingKing = false;

    public KingOnScreen(SpriteBatch spriteBatch, Float squareSize, Vector2 initialPosition) {
        this.spriteBatch = spriteBatch;

        Texture kingTexture = new Texture("b_king.png");
        kingSprite = new Sprite(kingTexture);
        kingSprite.setSize(squareSize, squareSize);
        kingRectangle = new Rectangle();
        effectivePosition = initialPosition;
        kingSprite.setCenter(initialPosition.x, initialPosition.y);
    }

    public void drag(Vector2 cursorPosition) {
        if (Gdx.input.justTouched()) {
            if (!draggingKing) {
                if (kingRectangle.contains(cursorPosition)) {
                    draggingKing = true;
                }
            } else {
                draggingKing = false;
                kingSprite.setCenter(effectivePosition.x, effectivePosition.y);
            }
        }

        if (draggingKing) {
            kingSprite.setPosition(
                cursorPosition.x - kingSprite.getWidth() / 2, cursorPosition.y - kingSprite.getHeight() / 2
            );
        }
    }

    public void draw() {
        kingSprite.draw(spriteBatch);
        kingRectangle.set(kingSprite.getX(), kingSprite.getY(), kingSprite.getWidth(), kingSprite.getHeight());
    }

}
