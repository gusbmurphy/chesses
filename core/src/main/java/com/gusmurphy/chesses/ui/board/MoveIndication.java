package com.gusmurphy.chesses.ui.board;

import com.badlogic.gdx.graphics.Color;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

class MoveIndication {

    private final Coordinates coordinates;
    private final Color color;

    private static final Color REGULAR_COLOR = new Color(0, 1, 1, 0.5f);
    private static final Color TAKING_COLOR = new Color(1, 0.5f, 0, 0.5f);

    public MoveIndication(Move move) {
        coordinates = move.coordinates();
        color = move.takes().isPresent() ? TAKING_COLOR : REGULAR_COLOR;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Color getColor() {
        return color;
    }
}
