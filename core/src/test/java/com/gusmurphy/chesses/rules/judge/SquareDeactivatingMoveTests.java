package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceFactory;
import org.junit.jupiter.api.Test;

import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static org.junit.jupiter.api.Assertions.*;

public class SquareDeactivatingMoveTests {

    @Test
    void afterAPieceHasMadeADeactivatingMoveAnotherPieceCannotMoveThere() {
        PieceFactory pieceFactory = new PieceFactory();
        Piece rook = pieceFactory.rook(WHITE, C3);
        Piece bishop = pieceFactory.bishop(BLACK, E3);

        Judge baseJudge = new Judge(new BoardState(rook, bishop));
        Judge squareDeactivationJudge = new SquareDeactivationRule(baseJudge);

        squareDeactivationJudge.submitMove(rook, C5);
        squareDeactivationJudge.submitMove(rook, H5);
        squareDeactivationJudge.submitMove(bishop, C5);

        assertEquals(E3, bishop.getCoordinates());
    }

}
