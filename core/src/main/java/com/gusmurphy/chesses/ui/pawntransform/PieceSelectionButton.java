package com.gusmurphy.chesses.ui.pawntransform;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.judge.PawnTransformReceiver;
import com.gusmurphy.chesses.rules.piece.PieceSprite;
import com.gusmurphy.chesses.rules.piece.PieceType;

class PieceSelectionButton extends Button {

    private final PieceType type;

    public PieceSelectionButton(PlayerColor color, PieceType type) {
        super(createSpriteDrawable(color, type));
        this.type = type;
    }

    public void setListener(PawnTransformReceiver receiver, PawnTransformRequestMenu menu) {
        clearListeners();
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                receiver.receiveNewType(type);
                menu.setVisible(false);
            }
        });
    }

    private static SpriteDrawable createSpriteDrawable(PlayerColor color, PieceType type) {
        Sprite sprite = PieceSprite.spriteFor(color, type);
        sprite.setSize(30, 30);
        return new SpriteDrawable(sprite);
    }

}
