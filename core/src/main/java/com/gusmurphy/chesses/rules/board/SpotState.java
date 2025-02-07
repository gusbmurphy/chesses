package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.judge.TurnChangeListener;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.Optional;

public interface SpotState extends TurnChangeListener {

    Optional<Piece> occupyingPiece();
    Optional<Piece> pieceTakeableBy(Piece other);

}
