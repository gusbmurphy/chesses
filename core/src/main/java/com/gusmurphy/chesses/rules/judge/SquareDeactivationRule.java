package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.square.SpecialSquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

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

        Coordinates originalCoordinates = piece.getCoordinates();
        super.submitMove(piece, coordinates);
        deactivatedCoordinates.add(originalCoordinates);
    }

    @Override
    public List<Move> getPossibleMoves() {
        return super.getPossibleMoves()
            .stream()
            .filter(move -> !deactivatedCoordinates.contains(move.coordinates()))
            .collect(Collectors.toList());
    }

    @Override
    public List<SpecialSquareState> getSpecialSquareStates() {
        return deactivatedCoordinates.stream()
            .map(SpecialSquareState::new)
            .collect(Collectors.toList());
    }
}
