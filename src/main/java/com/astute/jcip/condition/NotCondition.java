package com.astute.jcip.condition;

import com.astute.jcip.temporal.Condition;

public class NotCondition implements Condition {

    private final Condition condition;

    public NotCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean isSatisfied() {
        return !condition.isSatisfied();
    }
}
