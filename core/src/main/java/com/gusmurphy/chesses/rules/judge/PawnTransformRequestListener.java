package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;

public interface PawnTransformRequestListener {

    void requestNewTypeToTransformInto(PlayerColor forColor, PawnTransformReceiver receiver);

}
