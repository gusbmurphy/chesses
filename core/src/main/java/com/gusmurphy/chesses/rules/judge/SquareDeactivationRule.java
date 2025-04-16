package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.square.SpecialSquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.*;
import java.util.stream.Collectors;

public class SquareDeactivationRule extends JudgeDecorator {

    private final Set<Coordinates> deactivatedCoordinates = new HashSet<>();

    public SquareDeactivationRule(BaseJudge judge) {
        super(judge);
    }

    @Override
    public void submitMove(Piece piece, Coordinates coordinates) {
        if (deactivatedCoordinates.contains(coordinates)) {
            throw new IllegalMoveException(piece, coordinates);
        }

        super.submitMove(piece, coordinates);
        deactivatedCoordinates.add(coordinates);
    }

    @Override
    public List<SpecialSquareState> getSpecialSquareStates() {
        return deactivatedCoordinates.stream()
            .map(SpecialSquareState::new)
            .collect(Collectors.toList());
    }
}
