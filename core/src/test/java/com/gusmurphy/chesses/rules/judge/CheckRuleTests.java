package com.gusmurphy.chesses.rules.judge;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.square.coordinates.File;
import com.gusmurphy.chesses.rules.piece.DefaultPieces;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.gusmurphy.chesses.rules.PlayerColor.*;
import static com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates.*;
import static org.junit.jupiter.api.Assertions.*;

public class CheckRuleTests {

    @Test
    void aCheckablePieceCannotPutItselfIntoCheck() {
        Piece king = DefaultPieces.king(WHITE, C6);
        Piece rook = DefaultPieces.rook(BLACK, D4);

        BoardState boardState = new BoardState(king, rook);
        Judge judge = new Judge(boardState);
        judge = new CheckRule(judge);
        List<Move> movesForKing = judge
            .getPossibleMoves().stream().filter(move -> move.getMovingPiece() == king).collect(Collectors.toList());

        assertFalse(movesForKing.stream().anyMatch(move -> move.coordinates().file() == File.D));
    }

    @Test
    void aPieceCanPutTheOpposingColorInCheck() {
        Piece king = DefaultPieces.king(WHITE, C6);
        Piece rook = DefaultPieces.rook(BLACK, D4);

        BoardState boardState = new BoardState(king, rook);
        Judge judge = new Judge(boardState);
        judge = new CheckRule(judge);
        List<Move> movesForRook = judge
            .getPossibleMoves().stream().filter(move -> move.getMovingPiece() == rook).collect(Collectors.toList());

        assertTrue(movesForRook.stream().anyMatch(move -> move.coordinates() == D6));
    }

    @Test
    void theOnlyPossibleMoveMightBeForAnotherPieceToPreventCheck() {
        Piece king = DefaultPieces.king(WHITE, H1);
        Piece pawnA = DefaultPieces.pawn(WHITE, G1);
        Piece pawnB = DefaultPieces.pawn(WHITE, G2);
        Piece savingRook = DefaultPieces.rook(WHITE, C4);
        Piece attackingRook  = DefaultPieces.rook(BLACK, H6);


        BoardState boardState = new BoardState(king, pawnA, pawnB, savingRook, attackingRook);
        Judge judge = new Judge(boardState);
        judge = new CheckRule(judge);
        judge = new PlayerTurnRule(judge, WHITE);
        List<Move> moves = judge.getPossibleMoves();

        assertEquals(1, moves.size());
        assertEquals(savingRook, moves.get(0).getMovingPiece());
        assertEquals(H4, moves.get(0).coordinates());
    }

}
