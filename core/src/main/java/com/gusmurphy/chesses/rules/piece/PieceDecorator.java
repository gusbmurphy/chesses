package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardStateEventManager;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.movement.Move;

import java.util.List;

public abstract class PieceDecorator implements Piece {

    private final Piece decoratedPiece;

    PieceDecorator(Piece piece) {
        decoratedPiece = piece;
    }

    @Override
    public List<Move> currentPossibleMoves() {
        return decoratedPiece.currentPossibleMoves();
    }

    @Override
    public BoardCoordinates getCoordinates() {
        return decoratedPiece.getCoordinates();
    }

    @Override
    public PlayerColor color() {
        return decoratedPiece.color();
    }

    @Override
    public PieceType type() {
        return decoratedPiece.type();
    }

    @Override
    public void moveTo(BoardCoordinates coordinates) {
        decoratedPiece.moveTo(coordinates);
    }

    @Override
    public void take() {
        decoratedPiece.take();
    }

    @Override
    public void setEventManager(BoardStateEventManager manager) {
        decoratedPiece.setEventManager(manager);
    }

}
