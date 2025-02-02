package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.board.File;
import com.gusmurphy.chesses.rules.board.Rank;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.PieceMove;
import com.gusmurphy.chesses.rules.piece.movement.move.StaticMove;
import com.gusmurphy.chesses.rules.piece.movement.strategy.*;

import java.util.*;

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
        MovementStrategy castlingStrategy = createFullCastlingStrategy(color);

        Coordinates position = color == PlayerColor.WHITE ? Coordinates.E1 : Coordinates.E8;
        return new Piece(
            color,
            new CompositeMovementStrategy(base, castlingStrategy),
            position,
            KING
        );
    }

    private MovementStrategy createFullCastlingStrategy(PlayerColor color) {
        Rank rank = color == PlayerColor.WHITE ? Rank.ONE : Rank.EIGHT;

        MovementStrategy leftCastlingStrategy = createLeftCastlingStrategy(color, rank);
        List<Coordinates> leftUnoccupiedSpaces = Arrays.asList(
            Coordinates.with(File.D, rank),
            Coordinates.with(File.C, rank),
            Coordinates.with(File.B, rank)
        );
        leftCastlingStrategy = new RequiredUnoccupiedSpaceStrategy(leftUnoccupiedSpaces, leftCastlingStrategy);

        MovementStrategy rightCastlingStrategy = createRightCastlingStrategy(color, rank);
        List<Coordinates> rightUnoccupiedSpaces = Arrays.asList(
            Coordinates.with(File.F, rank),
            Coordinates.with(File.G, rank)
        );
        rightCastlingStrategy = new RequiredUnoccupiedSpaceStrategy(rightUnoccupiedSpaces, rightCastlingStrategy);

        MovementStrategy compositeStrategy = new CompositeMovementStrategy(
            leftCastlingStrategy, rightCastlingStrategy
        );
        return new TurnBasedMovementStrategy(1, compositeStrategy);
    }

    private MovementStrategy createLeftCastlingStrategy(PlayerColor color, Rank rank) {
        Piece rook = getLeftRook(color);
        Coordinates rookMove = Coordinates.with(File.D, rank);
        MovementStrategy strategy = createLinkedStrategy(-2, rookMove, rook);
        return limitStrategyByMovements(strategy, rook);
    }

    private MovementStrategy createRightCastlingStrategy(PlayerColor color, Rank rank) {
        Piece rook = getRightRook(color);
        Coordinates rookMove = Coordinates.with(File.F, rank);
        MovementStrategy strategy = createLinkedStrategy(2, rookMove, rook);
        return limitStrategyByMovements(strategy, rook);
    }

    private static MovementStrategy limitStrategyByMovements(MovementStrategy baseStrategy, Piece rook) {
        MovementStrategy limitedStrategy = new TurnBasedMovementStrategy(1, baseStrategy, rook);
        rook.subscribeToEvents(limitedStrategy);
        return limitedStrategy;
    }

    private static LinkedMovementStrategy createLinkedStrategy(int kingHorizontalMove, Coordinates rookMove, Piece rook) {
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
        Optional<Piece> existingRook = createdPieces
            .stream()
            .filter(piece -> piece.getCoordinates() == coordinates)
            .findFirst();

        return existingRook.orElseGet(() -> DefaultPieces.rook(color, coordinates));
    }

}
