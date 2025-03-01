package com.gusmurphy.chesses.rules.piece;

import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.PieceEventListener;
import com.gusmurphy.chesses.rules.board.square.SquareState;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.movement.move.Move;
import com.gusmurphy.chesses.rules.piece.movement.move.TakingMove;
import com.gusmurphy.chesses.rules.piece.movement.strategy.MovementStrategy;
import com.gusmurphy.chesses.rules.PlayerColor;
import org.jetbrains.annotations.Debug.Renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Renderer(text = "color.toString() + \" \" + type.toString() + \" \" + coordinates.toString()")
public class Piece {

    private final PlayerColor color;
    private MovementStrategy movementStrategy;
    private Coordinates coordinates;
    private PieceType type;
    private final List<PieceEventListener> eventListeners = new ArrayList<>();
    private BoardState boardState;
    private Integer boardId;
    private MovementStrategyProvider movementStrategyProvider;

    // TODO: These constructors are ugly
    protected Piece(
        PlayerColor color,
        MovementStrategy movementStrategy,
        Coordinates coordinates,
        PieceType type
    ) {
        this.color = color;
        this.movementStrategy = movementStrategy;
        this.movementStrategy.setRelevantPiece(this);
        this.coordinates = coordinates;
        this.type = type;

        eventListeners.add(movementStrategy);
    }

    // TODO: Remove these other constructors
    public Piece(MovementStrategy strategy, Coordinates coordinates) {
        this(
            PlayerColor.WHITE,
            strategy,
            coordinates,
            PieceType.KING
        );
    }

    public Piece(PlayerColor color, Coordinates coordinates, PieceType type) {
        this.color = color;
        this.coordinates = coordinates;
        this.type = type;
    }

    public Piece(Piece other) {
        color = other.color;
        movementStrategy = other.movementStrategy;
        coordinates = other.coordinates;
        type = other.type;
        boardId = other.boardId;
    }

    // Really only pawns should have this...
    public void transformTo(PieceType newType) {
        type = newType;
        movementStrategy = movementStrategyProvider.movementStrategyFor(type);
        eventListeners.forEach(listener -> listener.onPieceEvent(PieceEvent.TRANSFORMED, this));
    }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;

        if (boardId == null) {
            boardId = boardState.getNextId();
        }
    }

    public boolean isCheckable() {
        return type == PieceType.KING;
    }

    public void subscribeToEvents(PieceEventListener listener) {
        eventListeners.add(listener);
    }

    public List<Move> currentPossibleMoves() {
        List<Move> moves = movementStrategy
            .possibleMovesFrom(coordinates)
            .stream()
            .map(move -> new Move(move, this))
            .collect(Collectors.toList());

        List<Move> legalMoves = moves.stream().map(this::getAllLegalMovesFor).flatMap(List::stream).collect(Collectors.toList());

        legalMoves = filterMustTakeMoves(legalMoves);
        legalMoves = filterRequiredUnoccupiedMoves(legalMoves);
        legalMoves = filterSafeSpaceMoves(legalMoves);
        legalMoves = filterTakeDisallowedMoves(legalMoves);
        legalMoves = uniqueMovesBycoordinates(legalMoves);

        return legalMoves;
    }

    public boolean threatens(PlayerColor otherColor, Coordinates coordinates) {
        if (otherColor == color) {
            return false;
        }

        return currentPossibleMoves()
            .stream()
            .filter(move -> move.coordinates() == coordinates)
            .anyMatch(move -> !move.takeDisallowed());
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public PlayerColor color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    public void moveTo(Coordinates coordinates) {
        this.coordinates = coordinates;
        eventListeners.forEach(listener -> listener.onPieceEvent(PieceEvent.MOVED, this));
    }

    public void take() {
        eventListeners.forEach(listener -> listener.onPieceEvent(PieceEvent.TAKEN, this));
    }

    public boolean sameBoardIdAs(Piece other) {
        return Objects.equals(boardId, other.boardId);
    }

    public void setMovementStrategyProvider(MovementStrategyProvider provider) {
        movementStrategyProvider = provider;
    }

    protected void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
        movementStrategy.setRelevantPiece(this);
        eventListeners.add(movementStrategy);
    }

    private List<Move> getAllLegalMovesFor(Move move) {
        List<Move> legalMoves = new ArrayList<>();

        SquareState squareState = boardState.getStateAt(move.coordinates());
        if (moveCanTake(move, squareState)) {
            addTakingMove(move, legalMoves, squareState);
        } else if (noPieceAt(squareState)) {
            addMoveAndAnyContinuedMoves(move, legalMoves);
        }

        return legalMoves;
    }

    private static boolean moveCanTake(Move move, SquareState squareState) {
        return squareState.pieceTakeableBy(move.getMovingPiece()).isPresent();
    }

    private static void addTakingMove(Move move, List<Move> legalMoves, SquareState squareState) {
        legalMoves.add(new TakingMove(move.getMovingPiece(), move, squareState.pieceTakeableBy(move.getMovingPiece()).get()));
    }

    private static boolean noPieceAt(SquareState squareState) {
        return !squareState.occupyingPiece().isPresent();
    }

    private void addMoveAndAnyContinuedMoves(Move move, List<Move> legalMoves) {
        legalMoves.add(move);
        List<Move> continuedMoves = new ArrayList<>();

        move.next().map(nextMove ->
            continuedMoves.addAll(
                getAllLegalMovesFor(new Move(nextMove, move.getMovingPiece()))
            )
        );

        legalMoves.addAll(continuedMoves);
    }

    private static List<Move> filterMustTakeMoves(List<Move> legalMoves) {
        return legalMoves.stream().filter(move -> {
            if (move.mustTake()) {
                return move.takes().isPresent();
            }
            return true;
        }).collect(Collectors.toList());
    }

    private List<Move> filterRequiredUnoccupiedMoves(List<Move> legalMoves) {
        return legalMoves.stream().filter(move -> {
            for (Coordinates safeSpace : move.requiredUnoccupiedSpaces()) {
                if (boardState.getStateAt(safeSpace).occupyingPiece().isPresent()) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    private List<Move> filterSafeSpaceMoves(List<Move> moves) {
        return moves
            .stream()
            .filter(move -> {
                for (Coordinates safeSpace : move.requiredSafeSpaces()) {
                    if (boardState.getAllPieces().stream().anyMatch(piece -> piece.threatens(color, safeSpace))) return false;
                }
                return true;
            })
            .collect(Collectors.toList());
    }

    private static List<Move> filterTakeDisallowedMoves(List<Move> legalMoves) {
        return legalMoves
            .stream()
            .filter(move -> !move.takeDisallowed() || !move.takes().isPresent())
            .collect(Collectors.toList());
    }

    private static ArrayList<Move> uniqueMovesBycoordinates(List<Move> actualMoves) {
        HashMap<Coordinates, Move> movesBycoordinates = new HashMap<>();
        for (Move move : actualMoves) {
            movesBycoordinates.put(move.coordinates(), move);
        }
        return new ArrayList<>(movesBycoordinates.values());
    }

}
