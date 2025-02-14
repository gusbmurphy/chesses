package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.SquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.*;

public class Judge {

    protected final List<TurnChangeListener> turnChangeListeners = new ArrayList<>();
    protected final List<GameOverListener> gameOverListeners = new ArrayList<>();
    protected final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
        turnChangeListeners.add(boardState);
    }

    public void subscribeToTurnChange(TurnChangeListener listener) {
        turnChangeListeners.add(listener);
    }

    public void subscribeToGameOver(GameOverListener listener) {
        gameOverListeners.add(listener);
    }

    public void submitMove(Piece piece, Coordinates spot) {
        piece.currentPossibleMoves().stream().filter(move -> move.coordinates() == spot).findFirst().ifPresent(this::makeLegalMove);
    }

    public List<Move> getPossibleMoves() {
        List<Move> moves = new ArrayList<>();
        boardState.getAllPieces().forEach(piece -> {
            moves.addAll(piece.currentPossibleMoves());
        });
        return moves;
    }

    protected void notifyGameOverListeners(GameOverEvent event) {
        gameOverListeners.forEach(listener -> listener.onGameOverEvent(event));
    }

    protected void notifyTurnChangeListeners(PlayerColor newTurnColor) {
        turnChangeListeners.forEach(listener -> listener.onTurnChange(newTurnColor));
    }

    private void makeLegalMove(Move move) {
        takeOtherPieceIfPresent(move);
        moveMovingPiece(move);
        makeLinkedMoveIfPresent(move);
        distributeAnyEffectedSpots(move);
    }

    private void takeOtherPieceIfPresent(Move move) {
        move.takes().ifPresent(otherPiece -> {
            otherPiece.take();
            boardState.removePieceAt(otherPiece.getCoordinates());
        });
    }

    private static void moveMovingPiece(Move move) {
        move.getMovingPiece().moveTo(move.coordinates());
    }

    private static void makeLinkedMoveIfPresent(Move move) {
        move.linkedMove().ifPresent(Judge::moveMovingPiece);
    }

    private void distributeAnyEffectedSpots(Move move) {
        Map<Coordinates, SquareState> effectedSpots = move.effectedSpots();
        for (Map.Entry<Coordinates, SquareState> entry : effectedSpots.entrySet()) {
            boardState.setSpotState(entry.getKey(), entry.getValue());
        }
    }

}
