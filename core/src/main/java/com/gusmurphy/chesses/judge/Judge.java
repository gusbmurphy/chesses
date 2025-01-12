package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Judge {

    private final BoardState boardState;

    public Judge(BoardState boardState) {
        this.boardState = boardState;
    }

    public List<BoardCoordinates> movesFor(Piece piece) {
        if (boardState.coordinatesForPiece(piece.getPiece()).isPresent()) {
            List<BoardCoordinates> spotsWeCouldGo = new ArrayList<>();
            List<PossibleMove> possibleMoves = piece.currentPossibleMoves();

            possibleMoves.forEach(possibleMove -> {
                spotsWeCouldGo.add(possibleMove.spot());

                Optional<PossibleMove> next = possibleMove.next();

                while (next.isPresent()) {
                    spotsWeCouldGo.add(next.get().spot());
                    next = next.get().next();
                }
            });

            return spotsWeCouldGo;
        }
        return Collections.emptyList();
    }

}
