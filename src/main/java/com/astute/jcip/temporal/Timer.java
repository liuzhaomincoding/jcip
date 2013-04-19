package com.astute.jcip.temporal;

import com.astute.jcip.ClassInvariantViolation;
import com.astute.jcip.concurrency.annotations.Not;
import com.astute.jcip.concurrency.annotations.ThreadSafe;

import java.util.Date;

import static com.astute.jcip.temporal.Duration.millis;

@Not(ThreadSafe.class)
public final class Timer implements StopWatch {

    private final Clock clock;

    private Date started;
    private Date stopped;

    /**
     * Constructs and starts a stop watch.
     * @since 1.2
     * */
    public Timer(Clock clock) {
        Date now = clock.now();
        this.clock = clock;
        this.started = now;
        this.stopped = now;
    }

    @Deprecated
    public Date getStartDate() {
        return started;
    }

    // 设置 started 为当前时间
    @Override
    public void reset() {
        started = clock.now();
    }

    @Override
    public void lap() {
        stopped = clock.now();
    }

    @Override
    public Duration elapsedTime() {
        if (stopped.getTime() < started.getTime())
            throw new ClassInvariantViolation("please start the stop watch before stopping it");
        return millis(stopped.getTime() - started.getTime());
    }

}