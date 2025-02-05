package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;
import com.gusmurphy.chesses.rules.piece.movement.move.TakingMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Judge {

    protected final List<TurnChangeListener> turnChangeListeners = new ArrayList<>();
    protected final List<GameOverListener> gameOverListeners = new ArrayList<>();
    protected final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public void subscribeToTurnChange(TurnChangeListener listener) {
        turnChangeListeners.add(listener);
    }

    public void subscribeToGameOver(GameOverListener listener) {
        gameOverListeners.add(listener);
    }

    public void submitMove(Piece piece, Coordinates spot) {
        getLegalMove(piece, spot).ifPresent(this::makeLegalMove);
    }

    public List<PieceMove> getPossibleMoves() {
        List<PieceMove> pieceMoves = new ArrayList<>();
        boardState.getAllPieces().forEach(piece -> {
            List<Move> moves = possibleMovesFor(piece);
            moves.forEach(move -> pieceMoves.add(new PieceMove(move, piece)));
        });
        return pieceMoves;
    }

    protected List<Move> possibleMovesFor(Piece piece) {
        List<PieceMove> moves = piece.currentPossibleMoves();
        List<Move> legalMoves = moves.stream().map(this::getAllLegalMovesFor).flatMap(List::stream).collect(Collectors.toList());

        legalMoves = filterMustTakeMoves(legalMoves);
        legalMoves = filterRequiredUnoccupiedMoves(legalMoves);
        legalMoves = filterTakeDisallowedMoves(legalMoves);
        legalMoves = uniqueMovesBySpot(legalMoves);

        return legalMoves;
    }

    private static List<Move> filterMustTakeMoves(List<Move> legalMoves) {
        return legalMoves.stream().filter(move -> {
            if (move.mustTake()) {
                return move.takes().isPresent();
            }
            return true;
        }).collect(Collectors.toList());
    }

    private List<Move> filterRequiredUnoccupiedMoves(List<Move> legalMoves) {
        return legalMoves.stream().filter(move -> {
            for (Coordinates safeSpace : move.requiredUnoccupiedSpaces()) {
                if (boardState.getPieceAt(safeSpace).isPresent()) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    private static List<Move> filterTakeDisallowedMoves(List<Move> legalMoves) {
        return legalMoves
            .stream()
            .filter(move -> !move.takeDisallowed() || !move.takes().isPresent())
            .collect(Collectors.toList());
    }

    protected void notifyGameOverListeners(GameOverEvent event) {
        gameOverListeners.forEach(listener -> listener.onGameOverEvent(event));
    }

    protected void notifyTurnChangeListeners(PlayerColor newTurnColor) {
        turnChangeListeners.forEach(listener -> listener.onTurnChange(newTurnColor));
    }

    private Optional<PieceMove> getLegalMove(Piece piece, Coordinates spot) {
        return possibleMovesFor(piece)
            .stream()
            .filter(move -> move.spot() == spot)
            .map(move -> new PieceMove(move, piece))
            .findFirst();
    }

    private void makeLegalMove(PieceMove move) {
        move.takes().ifPresent(otherPiece -> {
            otherPiece.take();
            boardState.removePieceAt(move.spot());
        });

        move.getMovingPiece().moveTo(move.spot());

        move.linkedMove().ifPresent(linkedMove -> {
            linkedMove.getMovingPiece().moveTo(linkedMove.spot());
        });
    }

    private static ArrayList<Move> uniqueMovesBySpot(List<Move> actualMoves) {
        HashMap<Coordinates, Move> movesBySpot = new HashMap<>();
        for (Move move : actualMoves) {
            movesBySpot.put(move.spot(), move);
        }
        return new ArrayList<>(movesBySpot.values());
    }

    private List<Move> getAllLegalMovesFor(PieceMove move) {
        List<Move> legalMoves = new ArrayList<>();

        Optional<Piece> takeablePiece = boardState.getSpotStateAt(move.spot()).pieceTakeableBy(move.getMovingPiece());
        if (!takeablePiece.isPresent()) {
            legalMoves.add(move);
            legalMoves.addAll(getAllLegalMovesContinuingFrom(move));
        } else if (takeablePiece.get().color() != move.getMovingPiece().color()) {
            legalMoves.add(new TakingMove(move, takeablePiece.get()));
        }

        return legalMoves;
    }

    private List<Move> getAllLegalMovesContinuingFrom(PieceMove move) {
        List<Move> legalMoves = new ArrayList<>();

        move.next().map(nextMove ->
            legalMoves.addAll(
                getAllLegalMovesFor(new PieceMove(nextMove, move.getMovingPiece()))
            )
        );

        return legalMoves;
    }

}
