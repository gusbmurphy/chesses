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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Piece {

    private final PlayerColor color;
    private MovementStrategy movementStrategy;
    private Coordinates coordinates;
    private final PieceType type;
    private final List<PieceEventListener> eventListeners = new ArrayList<>();
    private BoardState boardState;

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
    }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
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
        legalMoves = uniqueMovesBySpot(legalMoves);

        return legalMoves;
    }

    public boolean threatens(PlayerColor otherColor, Coordinates coordinates) {
        if (otherColor == color) {
            return false;
        }

        return currentPossibleMoves()
            .stream()
            .filter(move -> move.spot() == coordinates)
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

    protected void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    private List<Move> getAllLegalMovesFor(Move move) {
        List<Move> legalMoves = new ArrayList<>();

        SquareState squareState = boardState.getStateAt(move.spot());
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

    private static ArrayList<Move> uniqueMovesBySpot(List<Move> actualMoves) {
        HashMap<Coordinates, Move> movesBySpot = new HashMap<>();
        for (Move move : actualMoves) {
            movesBySpot.put(move.spot(), move);
        }
        return new ArrayList<>(movesBySpot.values());
    }

}
