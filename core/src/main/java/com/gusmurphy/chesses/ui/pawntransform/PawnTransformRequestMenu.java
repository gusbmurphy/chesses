package com.gusmurphy.chesses.ui.pawntransform;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.judge.PawnTransformRequestListener;
import com.gusmurphy.chesses.rules.judge.PawnTransformReceiver;
import com.gusmurphy.chesses.rules.piece.PieceType;

public class PawnTransformRequestMenu extends Window implements PawnTransformRequestListener {

    private final PieceSelectionButton queenButton;

    public PawnTransformRequestMenu(Skin skin) {
        super("Select new type for pawn", skin);

        queenButton = new PieceSelectionButton(PlayerColor.WHITE, PieceType.QUEEN);
        super.add(queenButton);

        setVisible(false);
        setMovable(true);
        setResizable(true);
    }

    @Override
    public void requestNewTypeToTransformInto(PawnTransformReceiver receiver) {
        setVisible(true);
        queenButton.setListener(receiver, this);
    }
}
