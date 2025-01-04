package com.gusmurphy.chesses.board;

import java.util.Objects;

public class BoardCoordinates {
    private final File file;
    private final Rank rank;

    public BoardCoordinates(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public File file() {
        return file;
    }

    public Rank rank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardCoordinates that = (BoardCoordinates) o;
        return file == that.file && rank == that.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

}
