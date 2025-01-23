package com.gusmurphy.chesses.rules.judge;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseJudge implements Judge {

    protected final List<RuleEventListener> listeners = new ArrayList<>();

    @Override
    public void subscribeToEvents(RuleEventListener listener) {
        listeners.add(listener);
    }

}
