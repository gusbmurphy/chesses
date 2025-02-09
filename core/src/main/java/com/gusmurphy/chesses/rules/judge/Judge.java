package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.SpotState;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.move.TakingMove;

import java.util.*;
import java.util.stream.Collectors;

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
        getLegalMove(piece, spot).ifPresent(this::makeLegalMove);
    }

    public List<Move> getPossibleMoves() {
        List<Move> pieceMoves = new ArrayList<>();
        boardState.getAllPieces().forEach(piece -> {
            List<Move> moves = possibleMovesFor(piece);
            moves.forEach(move -> pieceMoves.add(new Move(move, piece)));
        });
        return pieceMoves;
    }

    protected List<Move> possibleMovesFor(Piece piece) {
        List<Move> moves = piece.currentPossibleMoves();
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
                if (boardState.getStateAt(safeSpace).occupyingPiece().isPresent()) return false;
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

    private Optional<Move> getLegalMove(Piece piece, Coordinates spot) {
        return possibleMovesFor(piece)
            .stream()
            .filter(move -> move.spot() == spot)
            .map(move -> new Move(move, piece))
            .findFirst();
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
        move.getMovingPiece().moveTo(move.spot());
    }

    private static void makeLinkedMoveIfPresent(Move move) {
        move.linkedMove().ifPresent(Judge::moveMovingPiece);
    }

    private void distributeAnyEffectedSpots(Move move) {
        Map<Coordinates, SpotState> effectedSpots = move.effectedSpots();
        for (Map.Entry<Coordinates, SpotState> entry : effectedSpots.entrySet()) {
            boardState.setSpotState(entry.getKey(), entry.getValue());
        }
    }

    private static ArrayList<Move> uniqueMovesBySpot(List<Move> actualMoves) {
        HashMap<Coordinates, Move> movesBySpot = new HashMap<>();
        for (Move move : actualMoves) {
            movesBySpot.put(move.spot(), move);
        }
        return new ArrayList<>(movesBySpot.values());
    }

    private List<Move> getAllLegalMovesFor(Move move) {
        List<Move> legalMoves = new ArrayList<>();

        SpotState spotState = boardState.getStateAt(move.spot());
        if (moveCanTake(move, spotState)) {
            legalMoves.add(new TakingMove(move.getMovingPiece(), move, spotState.pieceTakeableBy(move.getMovingPiece()).get()));
        } else if (!spotState.occupyingPiece().isPresent()) {
            legalMoves.add(move);
            List<Move> continuedMoves = new ArrayList<>();

            move.next().map(nextMove ->
                continuedMoves.addAll(
                    getAllLegalMovesFor(new Move(nextMove, move.getMovingPiece()))
                )
            );

            legalMoves.addAll(continuedMoves);
        }

        return legalMoves;
    }

    private static boolean moveCanTake(Move move, SpotState spotState) {
        return spotState.pieceTakeableBy(move.getMovingPiece()).isPresent();
    }

}
