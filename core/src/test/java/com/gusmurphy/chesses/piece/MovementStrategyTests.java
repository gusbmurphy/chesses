package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.File;
import com.gusmurphy.chesses.board.Rank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class MovementStrategyTests {

    @Test
    void aLinearMovementStrategyLimitsMovementToItsDirectionAndDistance() {
        MovementStrategy linear = new LinearMovementStrategy(Collections.singletonList(Direction.N), 1);
        List<BoardCoordinates> possibleMoves = linear.possibleMovesFrom(new BoardCoordinates(File.D, Rank.THREE));
        Assertions.assertEquals(possibleMoves.size(), 1);
        Assertions.assertEquals(possibleMoves.get(0), new BoardCoordinates(File.D, Rank.THREE));
    }

}
