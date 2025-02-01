package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.board.File;
import com.gusmurphy.chesses.rules.board.Rank;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;
import com.gusmurphy.chesses.rules.piece.movement.move.StaticMove;
import com.gusmurphy.chesses.rules.piece.movement.strategy.*;

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
        MovementStrategy base = new LinearMovementStrategy(Direction.every(), 1);
        MovementStrategy castlingStrategy = createCastlingStrategy(color);

        Coordinates position = color == PlayerColor.WHITE ? Coordinates.E1 : Coordinates.E8;
        return new Piece(
            color,
            new CompositeMovementStrategy(base, castlingStrategy),
            position,
            KING
        );
    }

    private MovementStrategy createCastlingStrategy(PlayerColor color) {
        Rank rank = color == PlayerColor.WHITE ? Rank.ONE : Rank.EIGHT;

        MovementStrategy leftCastlingStrategy = createLeftCastlingStrategy(color, rank);
        MovementStrategy rightCastlingStrategy = createRightCastlingStrategy(color, rank);

        MovementStrategy compositeStrategy = new CompositeMovementStrategy(
            leftCastlingStrategy, rightCastlingStrategy
        );
        return new TurnBasedMovementStrategy(1, compositeStrategy);
    }

    private MovementStrategy createLeftCastlingStrategy(PlayerColor color, Rank rank) {
        Piece rook = getLeftRook(color);
        Coordinates rookMove = Coordinates.with(File.D, rank);
        MovementStrategy strategy = createCastlingStrategy(-2, rookMove, rook);
        strategy = new TurnBasedMovementStrategy(1, strategy, rook);
        rook.subscribeToEvents(strategy);
        return strategy;
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
