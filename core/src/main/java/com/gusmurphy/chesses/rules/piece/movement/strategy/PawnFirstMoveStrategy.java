package com.gusmurphy.chesses.rules.piece.movement.strategy;


import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.square.EnPassantSquare;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.movement.move.EnPassantMove;
import com.gusmurphy.chesses.rules.piece.movement.move.UnassociatedMove;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gusmurphy.chesses.rules.board.Direction.N;
import static com.gusmurphy.chesses.rules.board.Direction.S;

public class PawnFirstMoveStrategy extends TurnBasedMovementStrategy {

    private final Piece piece;

    public PawnFirstMoveStrategy(Piece piece) {
        super(
            1,
            new RelativeMovementStrategy(0, piece.color() == PlayerColor.WHITE ? 2 : -2)
        );

        this.piece = piece;
    }

    @Override
    public List<UnassociatedMove> possibleMovesFrom(Coordinates position) {
        List<UnassociatedMove> moves = super.possibleMovesFrom(position);

        Optional<Coordinates> enPassantCoordinates = piece
            .getCoordinates()
            .coordinatesToThe(piece.color() == PlayerColor.WHITE ? N : S);

        return enPassantCoordinates.<List<UnassociatedMove>>map(coordinates -> moves.stream()
            .map(move -> new EnPassantMove(move.coordinates(), coordinates, new EnPassantSquare(piece)))
            .collect(Collectors.toList())
        ).orElse(moves);
    }

}
