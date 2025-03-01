package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.PieceEventListener;

import java.util.Optional;

public class TestPieceEventListener implements PieceEventListener {

    private Piece lastMovedPiece;
    private Piece lastPieceTaken;
    private Piece lastTransformedPiece;

    public Optional<Piece> getLastMovedPiece() {
        return Optional.ofNullable(lastMovedPiece);
    }

    public Optional<Piece> getLastPieceTaken() {
        return Optional.ofNullable(lastPieceTaken);
    }

    public Optional<Piece> getLastPieceTransformed() {
        return Optional.ofNullable(lastTransformedPiece);
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        switch (event) {
            case MOVED:
                lastMovedPiece = piece;
                break;
            case TAKEN:
                lastPieceTaken = piece;
                break;
            case TRANSFORMED:
                lastTransformedPiece = piece;
                break;
        }
    }

}
