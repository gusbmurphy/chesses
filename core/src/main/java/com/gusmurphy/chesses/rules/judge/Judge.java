package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.SquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.board.square.coordinates.Rank;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceType;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;

import java.util.*;
import java.util.stream.Collectors;

public class Judge {

    protected final List<TurnChangeListener> turnChangeListeners = new ArrayList<>();
    protected final List<GameOverListener> gameOverListeners = new ArrayList<>();
    protected final List<PawnTransformRequestListener> pawnTransformRequestListeners = new ArrayList<>();
    protected final BoardState boardState;
    private List<Move> latestPossibleMoves;
    private boolean waitingForPawnTransformDecision = false;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
        turnChangeListeners.add(boardState);
        latestPossibleMoves = getLatestPossibleMoves();
    }

    public void subscribeToTurnChange(TurnChangeListener listener) {
        turnChangeListeners.add(listener);
    }

    public void subscribeToGameOver(GameOverListener listener) {
        gameOverListeners.add(listener);
    }

    public void submitMove(Piece piece, Coordinates coordinates) {
        if (waitingForPawnTransformDecision) {
            return;
        }

        latestPossibleMoves
            .stream()
            .filter(move -> move.getMovingPiece() == piece)
            .filter(move -> move.coordinates() == coordinates)
            .findFirst()
            .ifPresent(this::makeLegalMove);

        latestPossibleMoves = getLatestPossibleMoves();
    }

    // TODO: Feels like we shouldn't be asking the Judge for moves...
    public List<Move> getPossibleMoves() {
        return latestPossibleMoves;
    }

    public void subscribeToPawnTransform(PawnTransformRequestListener listener) {
        pawnTransformRequestListeners.add(listener);
    }

    private List<Move> getLatestPossibleMoves() {
        return boardState.getAllPieces().stream()
            .map(Piece::currentPossibleMoves)
            .flatMap(List::stream)
            .collect(Collectors.toList());
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
        promptForAnyPawnTransformations();
    }

    private void promptForAnyPawnTransformations() {
        promptForTransformationsInRankWithColor(Rank.EIGHT, PlayerColor.WHITE);
        promptForTransformationsInRankWithColor(Rank.ONE, PlayerColor.BLACK);
    }

    private void promptForTransformationsInRankWithColor(Rank rank, PlayerColor color) {
        Coordinates.allIn(rank)
            .stream()
            .map(boardState::getStateAt)
            .map(SquareState::occupyingPiece)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(piece -> piece.type() == PieceType.PAWN)
            .filter(piece -> piece.color() == color)
            .forEach(this::requestNewTypeFromListeners);
    }

    private void requestNewTypeFromListeners(Piece pawnToTransform) {
        pawnTransformRequestListeners.forEach(listener -> {
            waitingForPawnTransformDecision = true;
            listener.requestNewTypeToTransformInto(
                pawnToTransform.color(),
                type -> {
                    waitingForPawnTransformDecision = false;
                    pawnToTransform.transformTo(type);
                }
            );
        });
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
