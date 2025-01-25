package com.gusmurphy.chesses.rules.piece.movement;

public class NoTakeMove extends MoveDecorator {

    public NoTakeMove(Move move) {
        super(move);
    }

    @Override
    public boolean takeDisallowed() {
        return true;
    }

}
