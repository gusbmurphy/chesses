package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;

import java.util.List;
import java.util.stream.Collectors;

import static com.gusmurphy.chesses.rules.PlayerColor.*;

public class PlayerTurnRule extends JudgeDecorator {

    private PlayerColor currentTurnColor;

    public PlayerTurnRule(Judge judge, PlayerColor initialTurnColor) {
        super(judge);
        currentTurnColor = initialTurnColor;
    }

    @Override
    public void submitMove(Piece piece, Coordinates spot) {
        if (piece.color() == currentTurnColor) {
            super.submitMove(piece, spot);
            currentTurnColor = currentTurnColor == BLACK ? WHITE : BLACK;
            notifyTurnChangeListeners(currentTurnColor);
        }
    }

    @Override
    public List<PieceMove> getPossibleMoves() {
        List<PieceMove> moves = super.getPossibleMoves();
        return moves
            .stream()
            .filter(pieceMove -> pieceMove.getMovingPiece().color() == currentTurnColor)
            .collect(Collectors.toList());
    }

}
