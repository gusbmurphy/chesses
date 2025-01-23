package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

public class PlayerTurnRule extends JudgeDecorator {

    public PlayerTurnRule(Judge judge, PlayerColor playerColor) {
        super(judge);
    }

    @Override
    public void submitMove(Piece piece, BoardCoordinates spot) {
    }

}
