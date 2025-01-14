package com.gusmurphy.chesses.board;

import com.gusmurphy.chesses.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardStateEventManager {

    private final Map<BoardStateEvent, List<BoardStateEventListener>> listeners = new HashMap<>();

    public BoardStateEventManager(BoardState boardState) {
        boardState.getAllPieces().forEach(piece -> piece.setEventManager(this));

        for (BoardStateEvent event : BoardStateEvent.values()) {
            listeners.put(event, new ArrayList<>());
        }
    }

    public void subscribe(BoardStateEventListener listener, BoardStateEvent event) {
        listeners.get(event).add(listener);
    }

    public void notify(BoardStateEvent event, Piece piece) {
        listeners.get(event).forEach(listener -> listener.onBoardStateEvent(event, piece));
    }

}
