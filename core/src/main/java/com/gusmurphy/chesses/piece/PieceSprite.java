package com.gusmurphy.chesses.piece;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.gusmurphy.chesses.player.PlayerColor.*;

public class PieceSprite {

    public static Sprite spriteFor(Piece piece) {
        return new Sprite(
            new Texture(fileNameFor(piece))
        );
    }

    private static String fileNameFor(Piece piece) {
        String fileName = piece.color() == WHITE ? "w_" : "b_";

        switch (piece.type()) {
            case KING:
                fileName += "king";
                break;
            case QUEEN:
                fileName += "queen";
                break;
            case ROOK:
                fileName += "rook";
                break;
            case BISHOP:
                fileName += "bishop";
                break;
            case KNIGHT:
                fileName += "knight";
                break;
            case PAWN:
                fileName += "pawn";
                break;
        }

        fileName += ".png";

        return fileName;
    }

}
