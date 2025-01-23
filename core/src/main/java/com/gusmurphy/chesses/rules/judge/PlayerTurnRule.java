package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.Piece;

import static com.gusmurphy.chesses.rules.PlayerColor.*;

public class PlayerTurnRule extends JudgeDecorator {

    private PlayerColor currentTurnColor;

    public PlayerTurnRule(Judge judge, PlayerColor initialTurnColor) {
        super(judge);
        currentTurnColor = initialTurnColor;
    }

    @Override
    public void submitMove(Piece piece, BoardCoordinates spot) {
        if (piece.color() == currentTurnColor) {
            super.submitMove(piece, spot);
            currentTurnColor = currentTurnColor == BLACK ? WHITE : BLACK;
            super.listeners.forEach(listener -> listener.onTurnChange(currentTurnColor));
        }
    }

}
