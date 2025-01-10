package com.gusmurphy.chesses.piece;

import com.badlogic.gdx.math.Vector2;

public interface PieceOnScreenMovementListener {
    void onPieceReleased(PieceOnScreen piece, Vector2 screenPosition);
}
