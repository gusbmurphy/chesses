package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.judge.LinearMovementStrategy;
import com.gusmurphy.chesses.player.PlayerColor;

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

}
