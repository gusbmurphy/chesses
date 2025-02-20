package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.board.square.coordinates.File;
import com.gusmurphy.chesses.rules.board.square.coordinates.Rank;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.move.StaticMove;
import com.gusmurphy.chesses.rules.piece.movement.strategy.*;

import java.util.*;

import static com.gusmurphy.chesses.rules.board.Direction.*;
import static com.gusmurphy.chesses.rules.board.Direction.W;
import static com.gusmurphy.chesses.rules.piece.PieceType.*;

// TODO: This should be able to make every kind of piece.
public class DefaultPieceFactory implements MovementStrategyProvider {

    private final List<Piece> createdPieces = new ArrayList<>();
    private final HashMap<PieceType, MovementStrategy> movementStrategies = new HashMap<>();

    private final static MovementStrategy BASE_KING_STRATEGY = new LinearMovementStrategy(Direction.every(), 1);

    public DefaultPieceFactory() {
        movementStrategies.put(ROOK, new LinearMovementStrategy(N, E, S, W));
        movementStrategies.put(BISHOP, new LinearMovementStrategy(NE, SE, SW, NW));
        movementStrategies.put(QUEEN, new LinearMovementStrategy(Direction.every()));
        movementStrategies.put(
            KNIGHT,
            new RelativeMovementStrategy(
                new RelativeMovementStrategy(1, 2),
                new RelativeMovementStrategy(-1, 2),
                new RelativeMovementStrategy(-1, -2),
                new RelativeMovementStrategy(1, -2),
                new RelativeMovementStrategy(2, 1),
                new RelativeMovementStrategy(-2, 1),
                new RelativeMovementStrategy(-2, -1),
                new RelativeMovementStrategy(2, -1)
            )
        );
    }

    public Piece rook(PlayerColor playerColor, Coordinates coordinates) {
        Piece rook = new PieceBuilder()
            .color(playerColor)
            .startingCoordinates(coordinates)
            .movementStrategy(movementStrategies.get(ROOK))
            .type(ROOK)
            .movementStrategyProvider(this)
            .build();

        createdPieces.add(rook);
        return rook;
    }

    public Piece bishop(PlayerColor playerColor, Coordinates coordinates) {
        Piece bishop = new PieceBuilder()
            .color(playerColor)
            .startingCoordinates(coordinates)
            .movementStrategy(movementStrategies.get(BISHOP))
            .type(BISHOP)
            .movementStrategyProvider(this)
            .build();

        createdPieces.add(bishop);
        return bishop;
    }

    public Piece pawn(PlayerColor playerColor, Coordinates coordinates) {
        Piece pawn = new PieceBuilder()
            .color(playerColor)
            .startingCoordinates(coordinates)
            .type(PAWN)
            .movementStrategyProvider(this)
            .build();

        MovementStrategy firstMove = new PawnFirstMoveStrategy(pawn);
        MovementStrategy movementStrategy = createPawnStrategy(playerColor, firstMove);
        pawn.setMovementStrategy(movementStrategy);

        createdPieces.add(pawn);
        return pawn;
    }

    public Piece king(PlayerColor color) {
        MovementStrategy castlingStrategy = createFullCastlingStrategy(color);

        Coordinates position = color == PlayerColor.WHITE ? Coordinates.E1 : Coordinates.E8;
        return new PieceBuilder()
            .color(color)
            .movementStrategy(new CompositeMovementStrategy(BASE_KING_STRATEGY, castlingStrategy))
            .startingCoordinates(position)
            .type(KING)
            .movementStrategyProvider(this)
            .build();
    }

    public Piece king(PlayerColor color, Coordinates position) {
        return new PieceBuilder()
            .color(color)
            .movementStrategy(BASE_KING_STRATEGY)
            .startingCoordinates(position)
            .type(KING)
            .movementStrategyProvider(this)
            .build();
    }

    public Piece queen(PlayerColor color, Coordinates position) {
        return new PieceBuilder()
            .color(color)
            .movementStrategy(movementStrategies.get(QUEEN))
            .startingCoordinates(position)
            .type(QUEEN)
            .movementStrategyProvider(this)
            .build();
    }

    public Piece knight(PlayerColor color, Coordinates position) {
        return new PieceBuilder()
            .color(color)
            .movementStrategy(movementStrategies.get(KNIGHT))
            .startingCoordinates(position)
            .type(KNIGHT)
            .movementStrategyProvider(this)
            .build();
    }

    public MovementStrategy movementStrategyFor(PieceType type) {
        return movementStrategies.get(type);
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
        List<Coordinates> leftSafeSpaces = Arrays.asList(
            Coordinates.with(File.D, rank),
            Coordinates.with(File.C, rank)
        );
        leftCastlingStrategy = new RequiredSafeSpaceStrategy(leftSafeSpaces, leftCastlingStrategy);

        MovementStrategy rightCastlingStrategy = createRightCastlingStrategy(color, rank);
        List<Coordinates> rightUnoccupiedAndSafeSpaces = Arrays.asList(
            Coordinates.with(File.F, rank),
            Coordinates.with(File.G, rank)
        );
        rightCastlingStrategy = new RequiredUnoccupiedSpaceStrategy(rightUnoccupiedAndSafeSpaces, rightCastlingStrategy);
        rightCastlingStrategy = new RequiredSafeSpaceStrategy(rightUnoccupiedAndSafeSpaces, rightCastlingStrategy);

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
            new Move(new StaticMove(rookMove), rook)
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

        return existingRook.orElseGet(() -> {
            Piece newRook = rook(color, coordinates);
            createdPieces.add(newRook);
            return newRook;
        });
    }

    private static MovementStrategy createPawnStrategy(PlayerColor color, MovementStrategy firstMove) {
        Direction movementDirection = color == PlayerColor.WHITE ? N : S;

        MovementStrategy regular = new LinearMovementStrategy(
            Collections.singletonList(movementDirection), 1
        );

        List<Direction> takingDirections = color == PlayerColor.WHITE ? Arrays.asList(NE, NW) : Arrays.asList(SE, SW);
        MovementStrategy takingMovement = new TakeOnlyMovementStrategy(
            new LinearMovementStrategy(takingDirections, 1)
        );

        return new CompositeMovementStrategy(
            new NoTakeMovementStrategy(firstMove),
            new NoTakeMovementStrategy(regular),
            new TakeOnlyMovementStrategy(takingMovement)
        );
    }

}
