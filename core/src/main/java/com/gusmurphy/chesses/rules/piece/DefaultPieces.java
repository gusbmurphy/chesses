package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.piece.movement.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gusmurphy.chesses.rules.board.Direction.*;
import static com.gusmurphy.chesses.rules.piece.PieceType.*;

// TODO: Would probably be good to have a builder for these, and some sort of "flyweight" implementation...
public class DefaultPieces {

    public static Piece king(PlayerColor color, Coordinates position) {
        return new Piece(
            color,
            new LinearMovementStrategy(every(), 1),
            position,
            KING
        );
    }

    public static Piece queen(PlayerColor color, Coordinates position) {
        return new Piece(
            color,
            new LinearMovementStrategy(every()),
            position,
            QUEEN
        );
    }

    public static Piece rook(PlayerColor color, Coordinates position) {
        return new Piece(
            color,
            new LinearMovementStrategy(N, E, S, W),
            position,
            ROOK
        );
    }

    public static Piece bishop(PlayerColor color, Coordinates position) {
        return new Piece(
            color,
            new LinearMovementStrategy(NE, SE, SW, NW),
            position,
            BISHOP
        );
    }

    public static Piece knight(PlayerColor color, Coordinates position) {
        return new Piece(
            color,
            new RelativeMovementStrategy(
                new RelativeMovementStrategy(1, 2),
                new RelativeMovementStrategy(-1, 2),
                new RelativeMovementStrategy(-1, -2),
                new RelativeMovementStrategy(1, -2),
                new RelativeMovementStrategy(2, 1),
                new RelativeMovementStrategy(-2, 1),
                new RelativeMovementStrategy(-2, -1),
                new RelativeMovementStrategy(2, -1)
            ),
            position,
            KNIGHT
        );
    }

    public static Piece pawn(PlayerColor color, Coordinates position) {
        Direction movementDirection = color == PlayerColor.WHITE ? N : S;

        MovementStrategy firstMove = new TurnBasedMovementStrategy(
            1, new LinearMovementStrategy(Collections.singletonList(movementDirection), 2)
        );
        MovementStrategy regular = new LinearMovementStrategy(
            Collections.singletonList(movementDirection), 1
        );

        List<Direction> takingDirections = color == PlayerColor.WHITE ? Arrays.asList(NE, NW) : Arrays.asList(SE, SW);
        MovementStrategy takingMovement = new TakeOnlyMovementStrategy(
            new LinearMovementStrategy(takingDirections, 1)
        );

        MovementStrategy movementStrategy = new CompositeMovementStrategy(
            new NoTakeMovementStrategy(firstMove),
            new NoTakeMovementStrategy(regular),
            new TakeOnlyMovementStrategy(takingMovement)
        );

        return new Piece(
            color,
            movementStrategy,
            position,
            PAWN
        );
    }

}
