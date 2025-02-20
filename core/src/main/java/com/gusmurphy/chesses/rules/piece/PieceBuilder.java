package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.strategy.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.NullMovementStrategy;

public class PieceBuilder {

    private PlayerColor color = PlayerColor.WHITE;
    private MovementStrategy movementStrategy = new NullMovementStrategy();
    private PieceType type = PieceType.KING;
    private Coordinates coordinates;
    private MovementStrategyProvider movementStrategyProvider;

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

    public PieceBuilder movementStrategyProvider(MovementStrategyProvider provider) {
        movementStrategyProvider = provider;
        return this;
    }

    public Piece build() {
        Piece piece = new Piece(color, movementStrategy, coordinates, type);
        piece.setMovementStrategyProvider(movementStrategyProvider);
        return piece;
    }

}
