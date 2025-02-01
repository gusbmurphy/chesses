package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;
import com.gusmurphy.chesses.rules.piece.movement.move.StaticMove;
import com.gusmurphy.chesses.rules.piece.movement.strategy.CompositeMovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.LinkedMovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.MovementStrategy;
import com.gusmurphy.chesses.rules.piece.movement.strategy.RelativeMovementStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        MovementStrategy strategy = createCastlingStrategy();

        Coordinates position = playerColor == PlayerColor.WHITE ? Coordinates.E1 : Coordinates.E8;
        return new Piece(
            playerColor,
            strategy,
            position,
            KING
        );
    }

    private MovementStrategy createCastlingStrategy() {
        Piece leftRook = getLeftRook();
        Piece rightRook = getRightRook();

        MovementStrategy leftCastlingStrategy = new LinkedMovementStrategy(
            new RelativeMovementStrategy(-2, 0),
            new PieceMove(new StaticMove(Coordinates.D1), leftRook)
        );

        MovementStrategy rightCastlingStrategy = new LinkedMovementStrategy(
            new RelativeMovementStrategy(2, 0),
            new PieceMove(new StaticMove(Coordinates.F1), rightRook)
        );

        return new CompositeMovementStrategy(leftCastlingStrategy, rightCastlingStrategy);
    }

    private Piece getLeftRook() {
        Optional<Piece> leftRook = createdPieces
            .stream()
            .filter(piece -> piece.getCoordinates() == Coordinates.A1)
            .findFirst();

        return leftRook.orElseGet(() -> DefaultPieces.rook(PlayerColor.WHITE, Coordinates.A1));
    }

    private Piece getRightRook() {
        Optional<Piece> rightRook = createdPieces
            .stream()
            .filter(piece -> piece.getCoordinates() == Coordinates.H1)
            .findFirst();

        return rightRook.orElseGet(() -> DefaultPieces.rook(PlayerColor.WHITE, Coordinates.H1));
    }

}
