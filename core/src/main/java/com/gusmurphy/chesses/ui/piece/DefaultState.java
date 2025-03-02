package com.gusmurphy.chesses.ui.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

class DefaultState extends PieceRepresentationState {

    public DefaultState(PieceRepresentation representation) {
        super(representation);
    }

    @Override
    public void processInput(Vector2 cursorPosition) {
        if (pieceWasJustClicked(cursorPosition)) {
            notifySelectionListeners();
        }
    }

    @Override
    public void setDragStatus(boolean isNowDragged) {
        if (isNowDragged) {
            representation.setState(new DraggedState(representation));
        }
    }

    private boolean pieceWasJustClicked(Vector2 cursorPosition) {
        return Gdx.input.justTouched()
            && representation.bounds.contains(cursorPosition);
    }

    private void notifySelectionListeners() {
        representation.selectionListeners.forEach(
            listener -> listener.onPieceSelected(representation.piece)
        );
    }

}
