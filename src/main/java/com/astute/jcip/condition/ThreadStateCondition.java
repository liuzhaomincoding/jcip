package com.astute.jcip.condition;

import com.astute.jcip.temporal.Condition;

public class ThreadStateCondition implements Condition {
    private final Thread thread;
    private final Thread.State state;

    public ThreadStateCondition(Thread thread, Thread.State state) {
        this.thread = thread;
        this.state = state;
    }

    @Override
    public boolean isSatisfied() {
        return thread.getState() == state;
    }

}
