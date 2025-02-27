package com.gusmurphy.chesses;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.gusmurphy.chesses.rules.judge.PawnTransformListener;
import com.gusmurphy.chesses.rules.judge.PawnTransformReceiver;

public class PawnTransformMenu extends Window implements PawnTransformListener {

    public PawnTransformMenu(Skin skin) {
        super("Select new type for pawn", skin);
        setVisible(false);
        setMovable(true);
        setResizable(true);
    }

    @Override
    public void requestNewTypeToTransformInto(PawnTransformReceiver receiver) {
        setVisible(true);
    }
}
