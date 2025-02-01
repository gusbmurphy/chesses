package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;
import com.gusmurphy.chesses.rules.piece.movement.move.StaticMove;
import com.gusmurphy.chesses.rules.piece.movement.strategy.LinkedMovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.RelativeMovementStrategy;

import java.util.ArrayList;
import java.util.List;

import static com.gusmurphy.chesses.rules.piece.PieceType.KING;

public class DefaultPieceFactory {

    private List<Piece> createdPieces = new ArrayList<>();

    public DefaultPieceFactory() {
    }

    public Piece rook(PlayerColor playerColor, Coordinates coordinates) {
        Piece rook = DefaultPieces.rook(playerColor, coordinates);
        createdPieces.add(rook);
        return rook;
    }

    public Piece king(PlayerColor playerColor) {
        Piece leftRook = getLeftRook();

        MovementStrategy castlingStrategy = new LinkedMovementStrategy(
            new RelativeMovementStrategy(-2, 0),
            new PieceMove(new StaticMove(Coordinates.D1), leftRook)
        );

        Coordinates position = playerColor == PlayerColor.WHITE ? Coordinates.E1 : Coordinates.E8;
        return new Piece(
            playerColor,
            castlingStrategy,
            position,
            KING
        );
    }

    private Piece getLeftRook() {
        return createdPieces.stream().filter(piece -> piece.getCoordinates() == Coordinates.A1).findFirst().get();
    }

}
