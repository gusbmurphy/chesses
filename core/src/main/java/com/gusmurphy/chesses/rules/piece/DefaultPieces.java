package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.piece.movement.*;

import java.util.Collections;

import static com.gusmurphy.chesses.rules.board.Direction.*;
import static com.gusmurphy.chesses.rules.piece.PieceType.*;

// TODO: Would probably be good to have a builder for these...
public class DefaultPieces {

    public static Piece king(PlayerColor color, BoardCoordinates position) {
        return new ConcretePiece(
            color,
            new LinearMovementStrategy(every(), 1),
            position,
            KING
        );
    }

    public static Piece queen(PlayerColor color, BoardCoordinates position) {
        return new ConcretePiece(
            color,
            new LinearMovementStrategy(every()),
            position,
            QUEEN
        );
    }

    public static Piece rook(PlayerColor color, BoardCoordinates position) {
        return new ConcretePiece(
            color,
            new LinearMovementStrategy(N, E, S, W),
            position,
            ROOK
        );
    }

    public static Piece bishop(PlayerColor color, BoardCoordinates position) {
        return new ConcretePiece(
            color,
            new LinearMovementStrategy(NE, SE, SW, NW),
            position,
            BISHOP
        );
    }

    public static Piece knight(PlayerColor color, BoardCoordinates position) {
        return new ConcretePiece(
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

    public static Piece pawn(PlayerColor color, BoardCoordinates position) {
        Direction movementDirection = color == PlayerColor.WHITE ? N : S;

        MovementStrategy movementStrategy = new CompositeMovementStrategy(
            new TurnBasedMovementStrategy(1, new LinearMovementStrategy(Collections.singletonList(movementDirection), 2)),
            new LinearMovementStrategy(Collections.singletonList(movementDirection), 1)
        );

        return new ConcretePiece(
            color,
            movementStrategy,
            position,
            PAWN
        );
    }

}
