package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.DefaultPieces;
import com.gusmurphy.chesses.piece.Piece;
import static com.gusmurphy.chesses.player.PlayerColor.*;

import java.util.Arrays;
import java.util.List;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;

public class StartingBoards {

    public static BoardState demo() {
        List<Piece> pieces = Arrays.asList(
            DefaultPieces.king(BLACK, A4),
            DefaultPieces.king(WHITE, H1),
            DefaultPieces.rook(WHITE, B3),
            DefaultPieces.bishop(WHITE, H4),
            DefaultPieces.queen(BLACK, D7),
            DefaultPieces.knight(BLACK, C4),
            DefaultPieces.pawn(BLACK, B7),
            DefaultPieces.pawn(WHITE, G2)
        );

        BoardState boardState = new BoardState();
        pieces.forEach(boardState::place);

        return boardState;
    }

}
