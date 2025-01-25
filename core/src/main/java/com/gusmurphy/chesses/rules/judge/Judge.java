package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;
import com.gusmurphy.chesses.rules.piece.movement.TakingMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Judge {

    protected final List<TurnChangeListener> listeners = new ArrayList<>();
    protected final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
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

        Optional<Piece> pieceAtSpot = boardState.getPieceAt(move.spot());
        if (!pieceAtSpot.isPresent()) {
            legalMoves.add(move);
            legalMoves.addAll(getAllLegalMovesContinuingFrom(move));
        } else if (pieceAtSpot.get().color() != move.getMovingPiece().color()) {
            legalMoves.add(new TakingMove(move.spot(), pieceAtSpot.get()));
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

    public void submitMove(Piece piece, Coordinates spot) {
        if (possibleMovesFor(piece).stream().anyMatch(move -> move.spot() == spot)) {
            Optional<Piece> pieceAtSpot = boardState.getPieceAt(spot);
            pieceAtSpot.ifPresent(otherPiece -> {
                otherPiece.take();
                boardState.removePieceAt(spot);
            });

            piece.moveTo(spot);
        }
    }

    protected List<Move> possibleMovesFor(Piece piece) {
        List<PieceMove> moves = piece.currentPossibleMoves();
        List<Move> legalMoves = new ArrayList<>();

        moves.forEach(move -> legalMoves.addAll(getAllLegalMovesFor(move)));

        return Judge.uniqueMovesBySpot(legalMoves);
    }

    public List<PieceMove> getPossibleMoves() {
        List<PieceMove> pieceMoves = new ArrayList<>();
        boardState.getAllPieces().forEach(piece -> {
            List<Move> moves = possibleMovesFor(piece);
            moves.forEach(move -> pieceMoves.add(new PieceMove(move, piece)));
        });
        return pieceMoves;
    }

    public void subscribeToTurnChange(TurnChangeListener listener) {
        listeners.add(listener);
    }

}
