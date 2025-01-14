package com.gusmurphy.chesses.board.coordinates;

import com.gusmurphy.chesses.board.Direction;
import com.gusmurphy.chesses.board.File;
import com.gusmurphy.chesses.board.Rank;

import java.util.Arrays;
import java.util.Optional;

import static com.gusmurphy.chesses.board.File.*;
import static com.gusmurphy.chesses.board.Rank.*;

public enum BoardCoordinates {

    A1(A, ONE), A2(A, TWO), A3(A, THREE), A4(A, FOUR), A5(A, FIVE), A6(A, SIX), A7(A, SEVEN), A8(A, EIGHT),
    B1(B, ONE), B2(B, TWO), B3(B, THREE), B4(B, FOUR), B5(B, FIVE), B6(B, SIX), B7(B, SEVEN), B8(B, EIGHT),
    C1(C, ONE), C2(C, TWO), C3(C, THREE), C4(C, FOUR), C5(C, FIVE), C6(C, SIX), C7(C, SEVEN), C8(C, EIGHT),
    D1(D, ONE), D2(D, TWO), D3(D, THREE), D4(D, FOUR), D5(D, FIVE), D6(D, SIX), D7(D, SEVEN), D8(D, EIGHT),
    E1(E, ONE), E2(E, TWO), E3(E, THREE), E4(E, FOUR), E5(E, FIVE), E6(E, SIX), E7(E, SEVEN), E8(E, EIGHT),
    F1(F, ONE), F2(F, TWO), F3(F, THREE), F4(F, FOUR), F5(F, FIVE), F6(F, SIX), F7(F, SEVEN), F8(F, EIGHT),
    G1(G, ONE), G2(G, TWO), G3(G, THREE), G4(G, FOUR), G5(G, FIVE), G6(G, SIX), G7(G, SEVEN), G8(G, EIGHT),
    H1(H, ONE), H2(H, TWO), H3(H, THREE), H4(H, FOUR), H5(H, FIVE), H6(H, SIX), H7(H, SEVEN), H8(H, EIGHT);

    final File file;
    final Rank rank;

    BoardCoordinates(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public Optional<BoardCoordinates> coordinatesToThe(Direction direction) {
        int shiftedFileOrdinal = file.ordinal() + direction.horizontalValue();
        int shiftedRankOrdinal = rank.ordinal() + direction.verticalValue();

        return Arrays
            .stream(BoardCoordinates.values())
            .filter(c -> c.file.ordinal() == shiftedFileOrdinal && c.rank.ordinal() == shiftedRankOrdinal)
            .findFirst();
    }

    public Direction directionTo(BoardCoordinates move) {
        Direction directionOfMove;

        if (file == move.file) {
            directionOfMove = rank.ordinal() < move.rank.ordinal() ? Direction.N : Direction.S;
        } else if (rank == move.rank) {
            directionOfMove = file.ordinal() < move.file.ordinal() ? Direction.E : Direction.W;
        } else {
            int horizontalMovement = move.file.ordinal() - file.ordinal();
            int verticalMovement = move.rank.ordinal() - rank.ordinal();

            if (verticalMovement > 0) {
                directionOfMove = horizontalMovement > 0 ? Direction.NE : Direction.NW;
            } else {
                directionOfMove = horizontalMovement > 0 ? Direction.SE : Direction.SW;
            }
        }

        return directionOfMove;
    }

    @Override
    public String toString() {
        return file.toString().substring(0, 1).toLowerCase() + (rank.ordinal() + 1);
    }

}
