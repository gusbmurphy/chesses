package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.piece.PieceType;

public class TestPawnTransformListener implements PawnTransformListener {

    private PieceType typeToRespondWith;

    @Override
    public void requestNewTypeToTransformInto(PawnTransformReceiver receiver) {
        receiver.receiveNewType(typeToRespondWith);
    }

    public void respondWith(PieceType type) {
        typeToRespondWith = type;
    }

}
