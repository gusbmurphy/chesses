package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.movement.*;
import com.gusmurphy.chesses.player.PlayerColor;

import java.util.Collections;

import static com.gusmurphy.chesses.board.Direction.*;
import static com.gusmurphy.chesses.piece.PieceType.*;

// TODO: Would probably be good to have a builder for these...
public class DefaultPieces {

    public static Piece king(PlayerColor color, BoardCoordinates position) {
        return new Piece(
            color,
            new LinearMovementStrategy(every(), 1),
            position,
            KING
        );
    }

    public static Piece queen(PlayerColor color, BoardCoordinates position) {
        return new Piece(
            color,
            new LinearMovementStrategy(every()),
            position,
            QUEEN
        );
    }

    public static Piece rook(PlayerColor color, BoardCoordinates position) {
        return new Piece(
            color,
            new LinearMovementStrategy(N, E, S, W),
            position,
            ROOK
        );
    }

    public static Piece bishop(PlayerColor color, BoardCoordinates position) {
        return new Piece(
            color,
            new LinearMovementStrategy(NE, SE, SW, NW),
            position,
            BISHOP
        );
    }

    public static Piece knight(PlayerColor color, BoardCoordinates position) {
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

    public static Piece pawn(PlayerColor color, BoardCoordinates position) {
        Direction movementDirection = color == PlayerColor.WHITE ? N : S;

        MovementStrategy movementStrategy = new CompositeMovementStrategy(
            new TurnBasedMovementStrategy(1, new LinearMovementStrategy(Collections.singletonList(movementDirection), 2)),
            new LinearMovementStrategy(Collections.singletonList(movementDirection), 1)
        );

        return new Piece(
            color,
            movementStrategy,
            position,
            PAWN
        );
    }

}
