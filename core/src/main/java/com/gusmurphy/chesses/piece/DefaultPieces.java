package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.player.PlayerColor;

import static com.gusmurphy.chesses.board.Direction.*;

public class DefaultPieces {

    public static PieceColorAndMovement king(PlayerColor color) {
        return new PieceColorAndMovement(color, new LinearMovementStrategy(every(), 1));
    }

}
