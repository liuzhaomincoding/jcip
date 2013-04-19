package com.astute.jcip.condition;

import com.astute.jcip.temporal.Condition;

public class ThreadAliveCondition implements Condition {
    private final Thread thread;

    public ThreadAliveCondition(Thread thread) {
        this.thread = thread;
    }

    @Override
    public boolean isSatisfied() {
        return thread.isAlive();
    }
}
