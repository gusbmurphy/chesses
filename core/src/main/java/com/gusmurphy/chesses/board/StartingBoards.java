package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.DefaultPieces;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.player.PlayerColor;

import java.util.Arrays;
import java.util.List;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.*;

public class StartingBoards {

    public static BoardState demo() {
        List<Piece> pieces = Arrays.asList(
            DefaultPieces.king(PlayerColor.BLACK, A4),
            DefaultPieces.king(PlayerColor.WHITE, H1),
            DefaultPieces.rook(PlayerColor.WHITE, B3),
            DefaultPieces.bishop(PlayerColor.WHITE, H4),
            DefaultPieces.queen(PlayerColor.BLACK, D7),
            DefaultPieces.knight(PlayerColor.BLACK, C4)
        );

        BoardState boardState = new BoardState();
        pieces.forEach(boardState::place);

        return boardState;
    }

}
