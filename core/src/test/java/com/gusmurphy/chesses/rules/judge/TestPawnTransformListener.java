package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.piece.PieceType;

public class TestPawnTransformListener implements PawnTransformListener {

    private Response response;

    @Override
    public void requestNewTypeToTransformInto(PawnTransformReceiver receiver) {
        response.respond(receiver);
    }

    public void respondWith(PieceType type) {
        response = receiver -> receiver.receiveNewType(type);
    }

    public void dontRespond() {
        response = receiver -> {
        };
    }

    interface Response {
        void respond(PawnTransformReceiver receiver);
    }

}
