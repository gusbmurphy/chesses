package com.gusmurphy.chesses.ui.pawntransform;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.judge.PawnTransformRequestListener;
import com.gusmurphy.chesses.rules.judge.PawnTransformReceiver;
import com.gusmurphy.chesses.rules.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class PawnTransformRequestMenu extends Window implements PawnTransformRequestListener {

    private final PlayerColor color;
    private final List<PieceSelectionButton> buttons;

    public PawnTransformRequestMenu(Skin skin, PlayerColor color) {
        super("Select new type for pawn", skin);

        this.color = color;

        buttons = new ArrayList<>();
        buttons.add(new PieceSelectionButton(color, PieceType.QUEEN));
        buttons.add(new PieceSelectionButton(color, PieceType.ROOK));
        buttons.add(new PieceSelectionButton(color, PieceType.BISHOP));
        buttons.add(new PieceSelectionButton(color, PieceType.KNIGHT));
        buttons.forEach(super::add);

        setSize(300, 150);
        setVisible(false);
        setMovable(true);
        setResizable(true);
    }

    @Override
    public void requestNewTypeToTransformInto(PlayerColor forColor, PawnTransformReceiver receiver) {
        if (forColor == color) {
            setVisible(true);
            buttons.forEach(button -> button.setListener(receiver, this));
        }
    }
}
