package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.DefaultPieces;
import com.gusmurphy.chesses.piece.Piece;
import com.gusmurphy.chesses.player.PlayerColor;

import java.util.Arrays;
import java.util.List;

import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.A4;
import static com.gusmurphy.chesses.board.coordinates.BoardCoordinates.H1;

public class StartingBoards {

    public static BoardState demo() {
        List<Piece> pieces = Arrays.asList(
            DefaultPieces.king(PlayerColor.BLACK, A4),
            DefaultPieces.king(PlayerColor.WHITE, H1)
        );

        BoardState boardState = new BoardState();
        pieces.forEach(boardState::place);

        return boardState;
    }

}
