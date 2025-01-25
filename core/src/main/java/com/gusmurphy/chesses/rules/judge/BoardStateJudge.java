package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;
import com.gusmurphy.chesses.rules.piece.movement.TakingMove;

import java.util.*;

public class BoardStateJudge extends Judge {

    private final BoardState boardState;

    public BoardStateJudge(BoardState boardState) {
        this.boardState = boardState;
    }

    protected List<Move> possibleMovesFor(Piece piece) {
        List<PieceMove> moves = piece.currentPossibleMoves();
        List<Move> legalMoves = new ArrayList<>();

        moves.forEach(move -> legalMoves.addAll(getAllLegalMovesFor(move)));

        return uniqueMovesBySpot(legalMoves);
    }

    @Override
    public List<PieceMove> getPossibleMoves() {
        List<PieceMove> pieceMoves = new ArrayList<>();
        boardState.getAllPieces().forEach(piece -> {
            List<Move> moves = possibleMovesFor(piece);
            moves.forEach(move -> pieceMoves.add(new PieceMove(move, piece)));
        });
        return pieceMoves;
    }

    private List<Move> getAllLegalMovesFor(PieceMove move) {
        List<Move> legalMoves = new ArrayList<>();

        Optional<Piece> pieceAtSpot = boardState.getPieceAt(move.spot());
        if (!pieceAtSpot.isPresent()) {
            legalMoves.add(move);

            move.next().map(nextMove ->
                legalMoves.addAll(
                    getAllLegalMovesFor(new PieceMove(nextMove, move.getMovingPiece()))
                )
            );
        } else if (pieceAtSpot.get().color() != move.getMovingPiece().color()) {
            legalMoves.add(new TakingMove(move.spot(), pieceAtSpot.get()));
        }

        return legalMoves;
    }

    @Override
    public void submitMove(Piece piece, BoardCoordinates spot) {
        if (possibleMovesFor(piece).stream().anyMatch(move -> move.spot() == spot)) {
            Optional<Piece> pieceAtSpot = boardState.getPieceAt(spot);
            pieceAtSpot.ifPresent(otherPiece -> {
                otherPiece.take();
                boardState.removePieceAt(spot);
            });

            piece.moveTo(spot);
        }
    }

    private static ArrayList<Move> uniqueMovesBySpot(List<Move> actualMoves) {
        HashMap<BoardCoordinates, Move> movesBySpot = new HashMap<>();
        for (Move move : actualMoves) {
            movesBySpot.put(move.spot(), move);
        }
        return new ArrayList<>(movesBySpot.values());
    }

}
