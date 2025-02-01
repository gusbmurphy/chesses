package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.File;
import com.gusmurphy.chesses.rules.board.Rank;
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

    private final List<Piece> createdPieces = new ArrayList<>();

    public DefaultPieceFactory() {
    }

    public Piece rook(PlayerColor playerColor, Coordinates coordinates) {
        Piece rook = DefaultPieces.rook(playerColor, coordinates);
        createdPieces.add(rook);
        return rook;
    }

    public Piece king(PlayerColor color) {
        MovementStrategy strategy = createCastlingStrategy(color);

        Coordinates position = color == PlayerColor.WHITE ? Coordinates.E1 : Coordinates.E8;
        return new Piece(
            color,
            strategy,
            position,
            KING
        );
    }

    private MovementStrategy createCastlingStrategy(PlayerColor color) {
        Rank rank = color == PlayerColor.WHITE ? Rank.ONE : Rank.EIGHT;

        MovementStrategy leftCastlingStrategy = createLeftCastlingStrategy(color, rank);
        MovementStrategy rightCastlingStrategy = createRightCastlingStrategy(color, rank);

        return new CompositeMovementStrategy(leftCastlingStrategy, rightCastlingStrategy);
    }

    private MovementStrategy createLeftCastlingStrategy(PlayerColor color, Rank rank) {
        Piece rook = getLeftRook(color);
        Coordinates rookMove = Coordinates.with(File.D, rank);
        return createCastlingStrategy(-2, rookMove, rook);
    }

    private MovementStrategy createRightCastlingStrategy(PlayerColor color, Rank rank) {
        Piece rook = getRightRook(color);
        Coordinates rookMove = Coordinates.with(File.F, rank);
        return createCastlingStrategy(2, rookMove, rook);
    }

    private static LinkedMovementStrategy createCastlingStrategy(int kingHorizontalMove, Coordinates rookMove, Piece rook) {
        return new LinkedMovementStrategy(
            new RelativeMovementStrategy(kingHorizontalMove, 0),
            new PieceMove(new StaticMove(rookMove), rook)
        );
    }

    private Piece getLeftRook(PlayerColor color) {
        Coordinates coordinates = color == PlayerColor.WHITE ? Coordinates.A1 : Coordinates.A8;
        return getRook(color, coordinates);
    }

    private Piece getRightRook(PlayerColor color) {
        Coordinates coordinates = color == PlayerColor.WHITE ? Coordinates.H1 : Coordinates.H8;
        return getRook(color, coordinates);
    }

    private Piece getRook(PlayerColor color, Coordinates coordinates) {
        Optional<Piece> rightRook = createdPieces
            .stream()
            .filter(piece -> piece.getCoordinates() == coordinates)
            .findFirst();

        return rightRook.orElseGet(() -> DefaultPieces.rook(color, coordinates));
    }

}
