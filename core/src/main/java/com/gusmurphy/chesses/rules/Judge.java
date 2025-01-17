package com.gusmurphy.chesses.rules;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.rules.piece.movement.Move;
import com.gusmurphy.chesses.rules.piece.movement.TakingMove;

import java.util.*;

public class Judge {

    private final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public List<Move> possibleMovesFor(Piece piece) {
        List<Move> moves = piece.currentPossibleMoves();
        List<Move> actualMoves = new ArrayList<>();

        moves.forEach(possibleMove ->
            {
                Optional<Piece> pieceAtSpot = boardState.getPieceAt(possibleMove.spot());
                if (!pieceAtSpot.isPresent()) {
                    actualMoves.add(possibleMove);

                    Optional<Move> next = possibleMove.next();

                    while (next.isPresent() && boardState.spotIsFree(next.get().spot())) {
                        actualMoves.add(next.get());
                        next = next.get().next();
                    }
                } else if (pieceAtSpot.get().color() != piece.color()) {
                    actualMoves.add(new TakingMove(possibleMove.spot(), pieceAtSpot.get()));
                }
            }
        );

        return uniqueMovesBySpot(actualMoves);
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
