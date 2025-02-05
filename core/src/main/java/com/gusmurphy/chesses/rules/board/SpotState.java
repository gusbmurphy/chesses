package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public class SpotState {

    private final Piece piece;

    public SpotState(Piece piece) {
        this.piece = piece;
    }

    public Optional<Piece> pieceTakeableBy(Piece other) {
        return Optional.ofNullable(piece);
    }

}
