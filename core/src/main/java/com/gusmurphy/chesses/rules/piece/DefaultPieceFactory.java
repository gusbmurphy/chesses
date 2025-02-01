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

    private List<Piece> createdPieces = new ArrayList<>();

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
        Piece leftRook = getLeftRook(color);
        Piece rightRook = getRightRook(color);

        MovementStrategy leftCastlingStrategy = new LinkedMovementStrategy(
            new RelativeMovementStrategy(-2, 0),
            new PieceMove(new StaticMove(Coordinates.with(File.D, rank)), leftRook)
        );

        MovementStrategy rightCastlingStrategy = new LinkedMovementStrategy(
            new RelativeMovementStrategy(2, 0),
            new PieceMove(new StaticMove(Coordinates.with(File.F, rank)), rightRook)
        );

        return new CompositeMovementStrategy(leftCastlingStrategy, rightCastlingStrategy);
    }

    private Piece getLeftRook(PlayerColor color) {
        Coordinates coordinates = color == PlayerColor.WHITE ? Coordinates.A1 : Coordinates.A8;
        Optional<Piece> leftRook = createdPieces
            .stream()
            .filter(piece -> piece.getCoordinates() == coordinates)
            .findFirst();

        return leftRook.orElseGet(() -> DefaultPieces.rook(color, coordinates));
    }

    private Piece getRightRook(PlayerColor color) {
        Coordinates coordinates = color == PlayerColor.WHITE ? Coordinates.H1 : Coordinates.H8;
        Optional<Piece> rightRook = createdPieces
            .stream()
            .filter(piece -> piece.getCoordinates() == coordinates)
            .findFirst();

        return rightRook.orElseGet(() -> DefaultPieces.rook(color, coordinates));
    }

}
