package com.gusmurphy.chesses.piece;

import com.gusmurphy.chesses.player.PlayerColor;

import static com.gusmurphy.chesses.board.Direction.*;

public class DefaultPieces {

    public static Piece king(PlayerColor color) {
        return new Piece(color, new LinearMovementStrategy(every(), 1));
    }

}
