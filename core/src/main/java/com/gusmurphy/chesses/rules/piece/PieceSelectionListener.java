package com.gusmurphy.chesses.rules.piece;

import com.badlogic.gdx.math.Vector2;

public interface PieceSelectionListener {
    void onPieceSelected(Piece piece);
    void onPieceReleased(Piece piece, Vector2 screenPosition);
}
