package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.board.square.coordinates.File;
import com.gusmurphy.chesses.rules.board.square.coordinates.Rank;
import com.gusmurphy.chesses.rules.piece.DefaultPieceFactory;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.ArrayList;
import java.util.List;

import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;

public class StartingBoards {

    public static BoardState regular() {
        List<Piece> pieces = new ArrayList<>();
        DefaultPieceFactory pieceFactory = new DefaultPieceFactory();

        for (File file : File.values()) {
            pieces.add(pieceFactory.pawn(WHITE, Coordinates.with(file, Rank.TWO)));
            pieces.add(pieceFactory.pawn(BLACK, Coordinates.with(file, Rank.SEVEN)));
        }

        pieces.add(pieceFactory.rook(WHITE, A1));
        pieces.add(pieceFactory.rook(BLACK, A8));
        pieces.add(pieceFactory.rook(WHITE, H1));
        pieces.add(pieceFactory.rook(BLACK, H8));

        pieces.add(pieceFactory.knight(WHITE, B1));
        pieces.add(pieceFactory.knight(BLACK, B8));
        pieces.add(pieceFactory.knight(WHITE, G1));
        pieces.add(pieceFactory.knight(BLACK, G8));

        pieces.add(pieceFactory.bishop(WHITE, C1));
        pieces.add(pieceFactory.bishop(BLACK, C8));
        pieces.add(pieceFactory.bishop(WHITE, F1));
        pieces.add(pieceFactory.bishop(BLACK, F8));

        pieces.add(pieceFactory.king(WHITE));
        pieces.add(pieceFactory.king(BLACK));

        pieces.add(pieceFactory.queen(WHITE, D1));
        pieces.add(pieceFactory.queen(BLACK, D8));

        BoardState boardState = new BoardState();
        pieces.forEach(boardState::place);

        return boardState;
    }

}
