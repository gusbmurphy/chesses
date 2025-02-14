package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class RequiredSafeSpaceMove extends UnassociatedMoveDecorator {

    private final List<Coordinates> requiredSafeSpaces;

    public RequiredSafeSpaceMove(UnassociatedMove move, List<Coordinates> requiredUnoccupiedSpaces) {
        super(move);
        this.requiredSafeSpaces = requiredUnoccupiedSpaces;
    }

    @Override
    public List<Coordinates> requiredSafeSpaces() {
        ArrayList<Coordinates> allRequiredSafeSpaces = new ArrayList<>(super.requiredSafeSpaces());
        allRequiredSafeSpaces.addAll(requiredSafeSpaces);
        return allRequiredSafeSpaces;
    }

}
