package com.gusmurphy.chesses.judge;

import com.gusmurphy.chesses.board.BoardState;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.board.coordinates.BoardCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

                possibleMove.continuedDirection().ifPresent(direction -> {
                    for (int spotsToCheck = possibleMove.continuedDistance(); spotsToCheck > 0; spotsToCheck--) {
                        possibleMove.spot().coordinatesToThe(direction).ifPresent(spotsWeCouldGo::add);
                    }
                });
            });

            return spotsWeCouldGo;
        }
        return Collections.emptyList();
    }

}
