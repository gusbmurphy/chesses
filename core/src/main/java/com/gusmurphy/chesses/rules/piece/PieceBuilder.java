package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.strategy.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.NullMovementStrategy;

public class PieceBuilder {

    private PlayerColor color = PlayerColor.WHITE;
    private MovementStrategy movementStrategy = new NullMovementStrategy();
    private PieceType type = PieceType.KING;
    private Coordinates coordinates;

    public PieceBuilder() {
    }

    public PieceBuilder color(PlayerColor color) {
        this.color = color;
        return this;
    }

    public PieceBuilder movementStrategy(MovementStrategy strategy) {
        movementStrategy = strategy;
        return this;
    }

    public PieceBuilder type(PieceType type) {
        this.type = type;
        return this;
    }

    public PieceBuilder startingCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Piece build() {
        return new Piece(color, movementStrategy, coordinates, type);
    }

}
