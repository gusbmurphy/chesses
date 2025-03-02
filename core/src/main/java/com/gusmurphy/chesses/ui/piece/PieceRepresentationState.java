package com.gusmurphy.chesses.ui.piece;

import com.badlogic.gdx.math.Vector2;

public abstract class PieceRepresentationState {
    PieceRepresentation representation;

    PieceRepresentationState(PieceRepresentation representation) {
        this.representation = representation;
    }

    public abstract void processInput(Vector2 cursorPosition);
    public abstract void setDragStatus(boolean isNowDragged);

}
