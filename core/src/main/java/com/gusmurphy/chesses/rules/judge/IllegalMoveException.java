package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

public class IllegalMoveException extends RuntimeException {

    public IllegalMoveException(Piece piece, Coordinates coordinates) {
        super(piece + " cannot move to " + coordinates);
    }

}
