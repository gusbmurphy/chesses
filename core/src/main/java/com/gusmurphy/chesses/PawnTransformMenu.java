package com.gusmurphy.chesses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.gusmurphy.chesses.rules.judge.PawnTransformListener;
import com.gusmurphy.chesses.rules.judge.PawnTransformReceiver;

public class PawnTransformMenu extends Window implements PawnTransformListener {

    public PawnTransformMenu(Skin skin) {
        super("Select new type for pawn", skin);

        Sprite queenSprite = new Sprite(new Texture("w_queen.png"));
        queenSprite.setSize(30, 30);
        Button queenButton = new ImageButton(new SpriteDrawable(queenSprite));
        super.add(queenButton);

        setVisible(false);
        setMovable(true);
        setResizable(true);
    }

    @Override
    public void requestNewTypeToTransformInto(PawnTransformReceiver receiver) {
        setVisible(true);
    }
}
