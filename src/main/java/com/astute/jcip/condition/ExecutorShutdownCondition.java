package com.astute.jcip.condition;

import com.astute.jcip.temporal.Condition;

import java.util.concurrent.ExecutorService;

public class ExecutorShutdownCondition implements Condition {

    private final ExecutorService executor;

    public ExecutorShutdownCondition(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public boolean isSatisfied() {
        return executor.isShutdown();
    }
}
