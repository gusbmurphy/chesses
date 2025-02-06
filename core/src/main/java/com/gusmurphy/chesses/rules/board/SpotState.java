package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public interface SpotState {

    Optional<Piece> occupyingPiece();

}
