package com.gusmurphy.chesses.rules.board;

import com.gusmurphy.chesses.rules.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardStateEventManager {

    private final Map<PieceEvent, List<PieceEventListener>> listeners = new HashMap<>();

    public BoardStateEventManager(BoardState boardState) {
        boardState.getAllPieces().forEach(piece -> piece.setEventManager(this));

        for (PieceEvent event : PieceEvent.values()) {
            listeners.put(event, new ArrayList<>());
        }
    }

    public void subscribe(PieceEventListener listener, PieceEvent event) {
        listeners.get(event).add(listener);
    }

    public void notify(PieceEvent event, Piece piece) {
        listeners.get(event).forEach(listener -> listener.onBoardStateEvent(event, piece));
    }

}
