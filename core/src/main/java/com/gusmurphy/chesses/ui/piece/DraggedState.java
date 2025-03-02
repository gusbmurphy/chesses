package com.gusmurphy.chesses.ui.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

class DraggedState extends PieceRepresentationState {

    public DraggedState(PieceRepresentation pieceRepresentation) {
        super(pieceRepresentation);
    }

    @Override
    public void processInput(Vector2 cursorPosition) {
        updateSpritePositionTo(cursorPosition);

        if (Gdx.input.justTouched()) {
            notifyListenersOfRelease(cursorPosition);
            updateSpriteCenter();
        }
    }

    @Override
    public void setDragStatus(boolean isNowDragged) {
        if (!isNowDragged) {
            representation.setState(new DefaultState(representation));
        }
    }

    private void updateSpritePositionTo(Vector2 newPosition) {
        representation.sprite.setPosition(
            newPosition.x - representation.sprite.getWidth() / 2,
            newPosition.y - representation.sprite.getHeight() / 2
        );
    }

    private void notifyListenersOfRelease(Vector2 cursorPosition) {
        representation.selectionListeners.forEach(listener -> listener.onPieceReleased(representation.piece, cursorPosition));
    }

    private void updateSpriteCenter() {
        representation.sprite.setCenter(representation.effectivePosition.x, representation.effectivePosition.y);
    }

}
