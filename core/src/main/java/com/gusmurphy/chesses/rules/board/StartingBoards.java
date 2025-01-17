package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates;

import static com.gusmurphy.chesses.rules.PlayerColor.*;

import java.util.Arrays;
import java.util.List;

public class StartingBoards {

    public static BoardState demo() {
        List<Piece> pieces = Arrays.asList(
            DefaultPieces.king(BLACK, BoardCoordinates.A4),
            DefaultPieces.king(WHITE, BoardCoordinates.H1),
            DefaultPieces.rook(WHITE, BoardCoordinates.B3),
            DefaultPieces.bishop(WHITE, BoardCoordinates.H4),
            DefaultPieces.queen(BLACK, BoardCoordinates.D7),
            DefaultPieces.knight(BLACK, BoardCoordinates.C4),
            DefaultPieces.pawn(BLACK, BoardCoordinates.B7),
            DefaultPieces.pawn(WHITE, BoardCoordinates.G2)
        );

        BoardState boardState = new BoardState();
        pieces.forEach(boardState::place);

        return boardState;
    }

}
