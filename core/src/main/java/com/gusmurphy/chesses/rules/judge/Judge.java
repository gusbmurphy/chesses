package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.SquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import org.jetbrains.annotations.NotNull;

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

    public void submitMove(Piece piece, Coordinates coordinates) {
        piece.currentPossibleMoves()
            .stream()
            .filter(move -> move.coordinates() == coordinates)
            .findFirst()
            .ifPresent(this::makeLegalMove);
    }

    // TODO: Feels like we shouldn't be asking the Judge for moves...
    public List<Move> getPossibleMoves() {
        return getLatestPossibleMoves();
    }

    private @NotNull List<Move> getLatestPossibleMoves() {
        List<Move> moves = new ArrayList<>();
        boardState.getAllPieces().forEach(piece -> {
            moves.addAll(piece.currentPossibleMoves());
        });
        return moves;
    }

    public List<Move> getPossibleMovesFor(Piece piece) {
        return piece.currentPossibleMoves();
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
        distributeAnyEffectedSquares(move);
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

    private void makeLinkedMoveIfPresent(Move move) {
        move.linkedMove().ifPresent(linkedMove -> {
            Piece linkedPiece = linkedMove.findMovingPieceOn(boardState);
            linkedPiece.moveTo(linkedMove.coordinates());
        });
    }

    private void distributeAnyEffectedSquares(Move move) {
        Map<Coordinates, SquareState> effectedSquares = move.effectedSquares();
        for (Map.Entry<Coordinates, SquareState> entry : effectedSquares.entrySet()) {
            boardState.setSquareState(entry.getKey(), entry.getValue());
        }
    }

}
