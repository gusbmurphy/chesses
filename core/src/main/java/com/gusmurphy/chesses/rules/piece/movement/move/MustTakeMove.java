package com.gusmurphy.chesses.rules.piece.movement.move;

public class MustTakeMove extends UnassociatedMoveDecorator {

    public MustTakeMove(UnassociatedMove move) {
        super(move);
    }

    @Override
    public boolean mustTake() {
        return true;
    }

}
