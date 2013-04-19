package com.astute.jcip.condition;

import com.astute.jcip.concurrency.Callable;
import com.astute.jcip.temporal.Condition;
import com.astute.jcip.temporal.SelfDescribingCondition;
import org.hamcrest.Matcher;
import org.junit.Assert;

import java.util.concurrent.ExecutorService;

public final class Conditions {

    public static Condition not(Condition condition) {
        return new NotCondition(condition);
    }

    public static Condition shutdown(ExecutorService service) {
        return new ExecutorShutdownCondition(service);
    }

    public static Condition isAlive(Thread thread) {
        return new ThreadAliveCondition(thread);
    }

    public static Condition isWaiting(Thread thread) {
        return new ThreadWaitingCondition(thread);
    }

    public static Condition is(Thread thread, Thread.State state) {
        return new ThreadStateCondition(thread, state);
    }

    public static void assertThat(Condition condition, Matcher<Boolean> booleanMatcher) {
        Assert.assertThat(condition.isSatisfied(), booleanMatcher);
    }

    public static void assertThat(String message, Condition condition, Matcher<Boolean> booleanMatcher) {
        Assert.assertThat(message, condition.isSatisfied(), booleanMatcher);
    }

    /** Useful when waiting for an assertion in tests, for example;
     * <p></p>
     * <code>WaitFor.waitOrTimeout(assertion(limit, is(5)), timeout(millis(500)))</code>
     * <p></p>
     * Not that if the actual value isn't updated by some asynchronous code, the matcher may never match so it'd be
     * pointless calling inside a <code>WaitFor.waitOrTimeout</code> call.
     *
     * @since 1.1
     * @deprecated use {@link #assertion(com.astute.jcip.concurrency.Callable, org.hamcrest.Matcher)} instead
     */
    @Deprecated
    public static <T> Condition assertion(T actual, Matcher<T> matcher) {
        return new MatcherCondition<T>(actual, matcher);
    }

    public static <T> SelfDescribingCondition assertion(Callable<T, RuntimeException> actual, Matcher<T> matcher) {
        return new SelfDescribingMatcherCondition(actual, matcher);
    }

}
