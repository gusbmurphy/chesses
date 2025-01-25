package com.gusmurphy.chesses.rules.piece.movement;

public class MustTakeMove extends MoveDecorator {

    public MustTakeMove(Move move) {
        super(move);
    }

    @Override
    public boolean mustTake() {
        return true;
    }

}
