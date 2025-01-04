package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.BoardCoordinates;
import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.File;
import com.gusmurphy.chesses.board.Rank;

import java.util.Collections;
import java.util.List;

public class LinearMovementStrategy implements MovementStrategy {

    private final List<Direction> directions;

    public LinearMovementStrategy(List<Direction> directions, int maxDistance) {
        this.directions = directions;
    }

    @Override
    public List<BoardCoordinates> possibleMovesFrom(BoardCoordinates position) {
        int fileOrdinal = position.file().ordinal();
        int rankOrdinal = position.rank().ordinal();
        Direction direction = directions.getFirst();

        switch (direction) {
            case N:
                rankOrdinal++;
                break;
            case NE:
                rankOrdinal++;
                fileOrdinal++;
                break;
            case E:
                fileOrdinal++;
                break;
            case SE:
                fileOrdinal++;
                rankOrdinal--;
                break;
            case S:
                rankOrdinal--;
                break;
            case SW:
                fileOrdinal--;
                rankOrdinal--;
                break;
            case W:
                fileOrdinal--;
                break;
            case NW:
                rankOrdinal++;
                fileOrdinal--;
                break;
        }

        if (rankOrdinal + 1 > Rank.values().length) {
            return Collections.emptyList();
        }

        File file = File.values()[fileOrdinal];
        Rank rank = Rank.values()[rankOrdinal];

        return Collections.singletonList(new BoardCoordinates(file, rank));
    }

}
