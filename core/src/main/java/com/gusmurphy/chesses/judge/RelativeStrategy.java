package com.gusmurphy.chesses.judge;

import com.badlogic.gdx.math.Vector2;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RelativeStrategy implements MovementStrategy {

    List<Vector2> movementVectors = new ArrayList<>();

    public RelativeStrategy(int x, int y) {
        movementVectors.add(new Vector2(x, y));
    }

    public RelativeStrategy(RelativeStrategy... strategies) {
        for (RelativeStrategy strategy : strategies) {
            movementVectors.addAll(strategy.movementVectors);
        }
    }

    @Override
    public List<PossibleMove> possibleMovesFrom(BoardCoordinates position) {
        return Collections.emptyList();
    }
}
