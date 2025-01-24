package com.gusmurphy.chesses.rules.judge;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseJudge implements Judge {

    protected final List<TurnChangeListener> listeners = new ArrayList<>();

    @Override
    public void subscribeToTurnChange(TurnChangeListener listener) {
        listeners.add(listener);
    }

}
