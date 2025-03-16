package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.List;
import java.util.stream.Collectors;

import static com.gusmurphy.chesses.rules.PlayerColor.*;

public class PlayerTurnRule extends JudgeDecorator {

    private PlayerColor currentTurnColor;
    private final int maxMovesPerTurn;
    private int movesMadeThisTurn = 0;

    public PlayerTurnRule(Judge judge, PlayerColor initialTurnColor) {
        super(judge);
        currentTurnColor = initialTurnColor;
        maxMovesPerTurn = 1;
    }

    public PlayerTurnRule(Judge judge, PlayerColor initialTurnColor, int movesPerTurn) {
        super(judge);
        currentTurnColor = initialTurnColor;
        this.maxMovesPerTurn = movesPerTurn;
    }

    @Override
    public void submitMove(Piece piece, Coordinates coordinates) {
        if (piece.color() == currentTurnColor) {
            super.submitMove(piece, coordinates);
            movesMadeThisTurn++;

            if (movesMadeThisTurn == maxMovesPerTurn) {
                movesMadeThisTurn = 0;
                currentTurnColor = currentTurnColor == BLACK ? WHITE : BLACK;
                notifyTurnChangeListeners(currentTurnColor);
            }
        }
    }

    @Override
    public List<Move> getPossibleMoves() {
        List<Move> moves = super.getPossibleMoves();
        return moves
            .stream()
            .filter(pieceMove -> pieceMove.getMovingPiece().color() == currentTurnColor)
            .collect(Collectors.toList());
    }

    @Override
    public void subscribeToTurnChange(TurnChangeListener listener) {
        listener.onTurnChange(currentTurnColor);
        super.subscribeToTurnChange(listener);
    }

}
