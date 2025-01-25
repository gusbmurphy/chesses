package com.gusmurphy.chesses.rules.board.coordinates;

import com.gusmurphy.chesses.rules.board.Direction;
import com.gusmurphy.chesses.rules.board.File;
import com.gusmurphy.chesses.rules.board.Rank;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static com.gusmurphy.chesses.rules.board.Rank.*;

public enum BoardCoordinates {

    A1(File.A, ONE), A2(File.A, TWO), A3(File.A, THREE), A4(File.A, FOUR), A5(File.A, FIVE), A6(File.A, SIX), A7(File.A, SEVEN), A8(File.A, EIGHT),
    B1(File.B, ONE), B2(File.B, TWO), B3(File.B, THREE), B4(File.B, FOUR), B5(File.B, FIVE), B6(File.B, SIX), B7(File.B, SEVEN), B8(File.B, EIGHT),
    C1(File.C, ONE), C2(File.C, TWO), C3(File.C, THREE), C4(File.C, FOUR), C5(File.C, FIVE), C6(File.C, SIX), C7(File.C, SEVEN), C8(File.C, EIGHT),
    D1(File.D, ONE), D2(File.D, TWO), D3(File.D, THREE), D4(File.D, FOUR), D5(File.D, FIVE), D6(File.D, SIX), D7(File.D, SEVEN), D8(File.D, EIGHT),
    E1(File.E, ONE), E2(File.E, TWO), E3(File.E, THREE), E4(File.E, FOUR), E5(File.E, FIVE), E6(File.E, SIX), E7(File.E, SEVEN), E8(File.E, EIGHT),
    F1(File.F, ONE), F2(File.F, TWO), F3(File.F, THREE), F4(File.F, FOUR), F5(File.F, FIVE), F6(File.F, SIX), F7(File.F, SEVEN), F8(File.F, EIGHT),
    G1(File.G, ONE), G2(File.G, TWO), G3(File.G, THREE), G4(File.G, FOUR), G5(File.G, FIVE), G6(File.G, SIX), G7(File.G, SEVEN), G8(File.G, EIGHT),
    H1(File.H, ONE), H2(File.H, TWO), H3(File.H, THREE), H4(File.H, FOUR), H5(File.H, FIVE), H6(File.H, SIX), H7(File.H, SEVEN), H8(File.H, EIGHT);

    final File file;
    final Rank rank;

    BoardCoordinates(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public File file() {
        return file;
    }

    public static BoardCoordinates with(File file, Rank rank) {
        return Arrays.stream(values()).filter(spot -> spot.file == file && spot.rank == rank).findFirst().get();
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
