package com.astute.jcip.temporal;

import com.astute.jcip.concurrency.Callable;

import java.util.concurrent.TimeoutException;

import static com.astute.jcip.temporal.Duration.millis;
import static com.astute.jcip.temporal.SimulateJUnitFailure.failOnTimeout;

public final class WaitFor {

    public static final Duration SLEEP_PERIOD = millis(50);

    private WaitFor() {
    }

    /** @since 1.1 */
    public static void waitOrTimeout(Condition condition, Timeout timeout) throws InterruptedException, TimeoutException {
        waitOrTimeout(condition, timeout, new ThreadSleep(SLEEP_PERIOD));
    }

    /**
     * 等待条件满足，直到超时发生
     * @param condition 条件
     * @param timeout 超时
     * @param sleeper 让执行线程睡眠的辅助类
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void waitOrTimeout(Condition condition, Timeout timeout, Sleeper sleeper) throws TimeoutException, InterruptedException {
        if (success(condition, timeout, sleeper))
            return;
        // 过期，抛出异常
        throw new TimeoutException();
    }

    /** @since 1.2 */
    public static <T, E extends Exception> void waitOrTimeout(Condition condition, Callable<T, E> onTimeout, Timeout timeout) throws InterruptedException, E {
        try {
            waitOrTimeout(condition, timeout);
        } catch (TimeoutException e) {
            onTimeout.call();
        }
    }

    /** @since 1.2 */
    public static void waitFor(SelfDescribingCondition condition, Timeout timeout) throws InterruptedException {
        waitOrTimeout(condition, failOnTimeout(condition), timeout);
    }

    public static void waitUntil(Timeout timeout) throws InterruptedException {
        while (!timeout.hasExpired())
            Thread.sleep(SLEEP_PERIOD.inMillis());
    }

    /**
     * 睡眠-检查 机制
     * 执行线程 先去判断有没有过期，如果没有过期，继续判断是否条件满足，如果满足，直接返回 true；如果超时，返回 false
     * 执行线程需要不停的检查，睡眠，再检查，再睡眠；效率不高，可以设计一种通知机制
     * @param condition
     * @param timeout
     * @param sleeper
     * @return
     * @throws InterruptedException
     */
    private static boolean success(Condition condition, Timeout timeout, Sleeper sleeper) throws InterruptedException {
        while (!timeout.hasExpired()) {
            if (condition.isSatisfied()) {
                return true;
            }
            sleeper.sleep();
        }
        return false;
    }

}