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
import java.util.stream.Stream;

public class Judge {

    protected final List<TurnChangeListener> turnChangeListeners = new ArrayList<>();
    protected final List<GameOverListener> gameOverListeners = new ArrayList<>();
    protected final List<PawnTransformListener> pawnTransformListeners = new ArrayList<>();
    protected final BoardState boardState;
    private List<Move> latestPossibleMoves;

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
        latestPossibleMoves
            .stream()
            .filter(move -> move.getMovingPiece() == piece)
            .filter(move -> move.coordinates() == coordinates)
            .findFirst()
            .ifPresent(this::makeLegalMove);

        promptForAnyPawnTransformations();

        latestPossibleMoves = getLatestPossibleMoves();
    }

    // TODO: Feels like we shouldn't be asking the Judge for moves...
    public List<Move> getPossibleMoves() {
        return latestPossibleMoves;
    }

    public void subscribeToPawnTransform(PawnTransformListener listener) {
        pawnTransformListeners.add(listener);
    }

    private List<Move> getLatestPossibleMoves() {
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
        distributeAnyEffectedSquares(move);
    }

    private void promptForAnyPawnTransformations() {
        getPawnsInRankEight().forEach(pawnToTransform -> {
            pawnTransformListeners.stream()
                .map(PawnTransformListener::requestNewTypeToTransformInto)
                .findFirst()
                .ifPresent(pawnToTransform::transformTo);
        });
    }

    private Stream<Piece> getPawnsInRankEight() {
        return Coordinates.allIn(Rank.EIGHT)
            .stream()
            .map(boardState::getStateAt)
            .filter(squareState -> squareState.occupyingPiece().isPresent() && squareState.occupyingPiece().get().type() == PieceType.PAWN)
            .map(SquareState::occupyingPiece)
            .filter(Optional::isPresent)
            .map(Optional::get);
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
