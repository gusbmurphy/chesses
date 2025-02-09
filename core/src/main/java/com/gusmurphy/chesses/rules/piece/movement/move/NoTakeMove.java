package com.gusmurphy.chesses.rules.piece.movement.move;

public class NoTakeMove extends MoveDecorator {

    public NoTakeMove(UnassociatedMove move) {
        super(move);
    }

    @Override
    public boolean takeDisallowed() {
        return true;
    }

}
