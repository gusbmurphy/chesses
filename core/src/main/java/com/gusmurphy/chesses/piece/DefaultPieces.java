package com.gusmurphy.chesses.piece;

import static com.gusmurphy.chesses.player.PlayerColor.*;

import static com.gusmurphy.chesses.board.Direction.*;

public class DefaultPieces {

    public static final Piece WHITE_KING = new Piece(WHITE, new LinearMovementStrategy(every(), 1));
    public static final Piece BLACK_KING = new Piece(BLACK, new LinearMovementStrategy(every(), 1));

}
