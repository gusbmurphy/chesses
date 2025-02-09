package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class RequiredUnoccupiedSpaceMove extends UnassociatedMoveDecorator {

    private final List<Coordinates> requiredSafeSpaces;

    public RequiredUnoccupiedSpaceMove(UnassociatedMove move, List<Coordinates> requiredUnoccupiedSpaces) {
        super(move);
        this.requiredSafeSpaces = requiredUnoccupiedSpaces;
    }

    @Override
    public List<Coordinates> requiredUnoccupiedSpaces() {
        ArrayList<Coordinates> allRequiredUnoccupiedSpaces = new ArrayList<>(super.requiredUnoccupiedSpaces());
        allRequiredUnoccupiedSpaces.addAll(requiredSafeSpaces);
        return allRequiredUnoccupiedSpaces;
    }

}
