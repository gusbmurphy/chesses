package com.gusmurphy.chesses.rules;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.piece.movement.MoveWithPiece;
import com.gusmurphy.chesses.rules.piece.movement.TakingMove;

import java.util.*;

public class Judge {

    private final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public List<Move> possibleMovesFor(Piece piece) {
        List<MoveWithPiece> moves = piece.currentPossibleMoves();
        List<Move> legalMoves = new ArrayList<>();

        moves.forEach(move -> {
            legalMoves.addAll(getAllLegalMovesFor(piece, move));
        });

        return uniqueMovesBySpot(legalMoves);
    }

    private List<Move> getAllLegalMovesFor(Piece piece, Move move) {
        List<Move> legalMoves = new ArrayList<>();

        Optional<Piece> pieceAtSpot = boardState.getPieceAt(move.spot());
        if (!pieceAtSpot.isPresent()) {
            legalMoves.add(move);

            move.next().map(nextMove ->
                legalMoves.addAll(
                    getAllLegalMovesFor(piece, nextMove)
                )
            );
        } else if (pieceAtSpot.get().color() != piece.color()) {
            legalMoves.add(new TakingMove(move.spot(), pieceAtSpot.get()));
        }

        return legalMoves;
    }

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
