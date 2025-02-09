package com.gusmurphy.chesses.rules.piece.movement.move;

import java.util.Optional;

public class LinkedMove extends MoveDecorator {

    private final PieceMove linkedMove;

    public LinkedMove(UnassociatedMove wrappedMove, PieceMove linkedMove) {
        super(wrappedMove);
        this.linkedMove = linkedMove;
    }

    @Override
    public Optional<PieceMove> linkedMove() {
        return Optional.of(linkedMove);
    }

}
