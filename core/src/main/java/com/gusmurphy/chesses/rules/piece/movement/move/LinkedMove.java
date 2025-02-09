package com.gusmurphy.chesses.rules.piece.movement.move;

import java.util.Optional;

public class LinkedMove extends UnassociatedMoveDecorator {

    private final Move linkedMove;

    public LinkedMove(UnassociatedMove wrappedMove, Move linkedMove) {
        super(wrappedMove);
        this.linkedMove = linkedMove;
    }

    @Override
    public Optional<Move> linkedMove() {
        return Optional.of(linkedMove);
    }

}
