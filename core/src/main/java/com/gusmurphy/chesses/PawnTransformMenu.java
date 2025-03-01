package com.gusmurphy.chesses;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.gusmurphy.chesses.rules.judge.PawnTransformListener;
import com.gusmurphy.chesses.rules.judge.PawnTransformReceiver;

public class PawnTransformMenu extends Window implements PawnTransformListener {

    public PawnTransformMenu(Skin skin) {
        super("Select new type for pawn", skin);

        TextButton queenButton = new TextButton("Queen", skin);
        TextButton rookButton = new TextButton("Rook", skin);
        TextButton bishopButton = new TextButton("Bishop", skin);
        TextButton knightButton = new TextButton("Knight", skin);
        super.add(queenButton, rookButton, bishopButton, knightButton);

        setVisible(false);
        setMovable(true);
        setResizable(true);
    }

    @Override
    public void requestNewTypeToTransformInto(PawnTransformReceiver receiver) {
        setVisible(true);
    }
}
