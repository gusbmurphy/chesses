package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.player.PlayerColor;

import static com.gusmurphy.chesses.board.Direction.*;
import static com.gusmurphy.chesses.piece.PieceType.*;

public class DefaultPieces {

    public static Piece king(PlayerColor color, BoardCoordinates position) {
        return new Piece(
            new PieceColorAndMovement(
                color,
                new LinearMovementStrategy(every(), 1)
            ),
            position,
            KING
        );
    }

}
