package com.gusmurphy.chesses.rules.piece.movement.move;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class RequiredUnoccupiedSpaceMove extends UnassociatedMoveDecorator {

    private final List<Coordinates> requiredUnoccupiedSpaces;

    public RequiredUnoccupiedSpaceMove(UnassociatedMove move, List<Coordinates> requiredUnoccupiedSpaces) {
        super(move);
        this.requiredUnoccupiedSpaces = requiredUnoccupiedSpaces;
    }

    @Override
    public List<Coordinates> requiredUnoccupiedSpaces() {
        ArrayList<Coordinates> allRequiredUnoccupiedSpaces = new ArrayList<>(super.requiredUnoccupiedSpaces());
        allRequiredUnoccupiedSpaces.addAll(requiredUnoccupiedSpaces);
        return allRequiredUnoccupiedSpaces;
    }

}
