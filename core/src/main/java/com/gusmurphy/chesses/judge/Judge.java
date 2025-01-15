package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;
import com.gusmurphy.chesses.piece.movement.PossibleMove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    private List<BoardCoordinates> spacesPieceCanMoveTo(Piece piece) {
        List<BoardCoordinates> spotsWeCouldGo = new ArrayList<>();
        List<PossibleMove> possibleMoves = piece.currentPossibleMoves();

        // TODO: Hmm...
        possibleMoves.forEach(possibleMove -> {
            if (boardState.spotIsFree(possibleMove.spot())) {
                spotsWeCouldGo.add(possibleMove.spot());

                Optional<PossibleMove> next = possibleMove.next();

                while (next.isPresent() && boardState.spotIsFree(next.get().spot())) {
                    spotsWeCouldGo.add(next.get().spot());
                    next = next.get().next();
                }
            }
        });

        return spotsWeCouldGo.stream().distinct().collect(Collectors.toList());
    }

}
