package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.movement.Move;
import com.gusmurphy.chesses.piece.movement.TakingMove;

import java.util.*;
import java.util.stream.Collectors;

public class Judge {

    private final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public List<BoardCoordinates> movesFor(Piece piece) {
        if (boardState.pieceIsOnBoard(piece)) {
            return spacesPieceCanMoveTo(piece);
        }
        return Collections.emptyList();
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

        HashMap<BoardCoordinates, Move> movesBySpot = new HashMap<>();
        for (Move move : actualMoves) {
            movesBySpot.put(move.spot(), move);
        }
        return new ArrayList<>(movesBySpot.values());
    }

    private List<BoardCoordinates> spacesPieceCanMoveTo(Piece piece) {
        List<BoardCoordinates> spotsWeCouldGo = new ArrayList<>();
        List<Move> moves = piece.currentPossibleMoves();

        moves.forEach(possibleMove ->
            spotsWeCouldGo.addAll(getAllMovesBranchingFrom(possibleMove))
        );

        return spotsWeCouldGo.stream().distinct().collect(Collectors.toList());
    }

    private List<BoardCoordinates> getAllMovesBranchingFrom(Move move) {
        List<BoardCoordinates> spotsWeCouldGo = new ArrayList<>();
        if (boardState.spotIsFree(move.spot())) {
            spotsWeCouldGo.add(move.spot());

            Optional<Move> next = move.next();

            while (next.isPresent() && boardState.spotIsFree(next.get().spot())) {
                spotsWeCouldGo.add(next.get().spot());
                next = next.get().next();
            }
        }
        return spotsWeCouldGo;
    }

}
