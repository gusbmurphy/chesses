package com.gusmurphy.chesses.ui.piece;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.PieceEventListener;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceSelectionListener;
import com.gusmurphy.chesses.rules.piece.PieceSprite;
import com.gusmurphy.chesses.ui.BoardRepresentation;

import static com.gusmurphy.chesses.ui.BoardRepresentation.*;

import java.util.ArrayList;
import java.util.List;

public class PieceRepresentation implements PieceEventListener {

    protected final Piece piece;
    protected final Rectangle bounds;
    protected final List<PieceSelectionListener> selectionListeners = new ArrayList<>();
    protected Sprite sprite;
    protected Vector2 effectivePosition;

    private final static float PIECE_TO_SQUARE_SIZE_RATIO = 0.8f;
    private final BoardRepresentation boardRepresentation;
    private final SpriteBatch spriteBatch;
    private PieceRepresentationState state;

    public PieceRepresentation(Piece piece, BoardRepresentation boardRepresentation) {
        this.piece = piece;
        piece.subscribeToEvents(this);
        this.spriteBatch = boardRepresentation.getSpriteBatch();

        this.boardRepresentation = boardRepresentation;

        effectivePosition = boardRepresentation.getScreenPositionForCenterOf(piece.getCoordinates());
        setSpriteFor(piece);
        bounds = new Rectangle();

        state = new DefaultState(this);
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        switch (event) {
            case MOVED:
                setEffectivePosition(boardRepresentation.getScreenPositionForCenterOf(this.piece.getCoordinates()));
                break;
            case TRANSFORMED:
                setSpriteFor(piece);
                break;
        }
    }

    public void subscribeToMovement(PieceSelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void setDragStatus(boolean isNowDragged) {
        state.setDragStatus(isNowDragged);
    }

    public void processInput(Vector2 cursorPosition) {
        state.processInput(cursorPosition);
    }

    public void setEffectivePosition(Vector2 position) {
        this.effectivePosition = position;
        sprite.setCenter(effectivePosition.x, effectivePosition.y);
    }

    public void draw() {
        sprite.draw(spriteBatch);
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    protected void setState(PieceRepresentationState newState) {
        state = newState;
    }

    private void setSpriteFor(Piece piece) {
        sprite = PieceSprite.spriteFor(piece);
        sprite.setSize(SQUARE_SIZE * PIECE_TO_SQUARE_SIZE_RATIO, SQUARE_SIZE * PIECE_TO_SQUARE_SIZE_RATIO);
        sprite.setCenter(effectivePosition.x, effectivePosition.y);
    }

}
