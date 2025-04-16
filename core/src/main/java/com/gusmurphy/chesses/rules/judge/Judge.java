package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.square.SpecialSquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.Collections;
import java.util.List;

public abstract class Judge {

    public abstract void subscribeToTurnChange(TurnChangeListener listener);
    public abstract void subscribeToGameOver(GameOverListener listener);
    public abstract void submitMove(Piece piece, Coordinates coordinates) throws IllegalMoveException;
    // TODO: Feels like we shouldn't be asking the Judge for moves...
    public abstract List<Move> getPossibleMoves();
    public abstract void subscribeToPawnTransform(PawnTransformRequestListener listener);

    public List<SpecialSquareState> getSpecialSquareStates() {
        return Collections.emptyList();
    }

}
