package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.File;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.PieceMove;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinates.*;
import static org.junit.jupiter.api.Assertions.*;

public class CheckRuleTests {

    @Test
    void aCheckablePieceCannotPutItselfIntoCheck() {
        Piece king = DefaultPieces.king(WHITE, C6);
        Piece rook = DefaultPieces.rook(BLACK, D4);

        BoardState boardState = new BoardState(king, rook);
        Judge judge = new BoardStateJudge(boardState);
        judge = new CheckRule(judge);
        List<PieceMove> movesForKing = judge
            .getPossibleMoves().stream().filter(move -> move.getMovingPiece() == king).collect(Collectors.toList());

        assertFalse(movesForKing.stream().anyMatch(move -> move.spot().file() == File.D));
    }

}
