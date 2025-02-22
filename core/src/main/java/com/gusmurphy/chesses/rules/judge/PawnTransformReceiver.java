package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.piece.PieceType;

public interface PawnTransformReceiver {
    void receiveNewType(PieceType type);
}
