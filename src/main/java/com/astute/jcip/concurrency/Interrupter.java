package com.astute.jcip.concurrency;

import com.astute.jcip.temporal.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * User: 智深
 * Date: 13-4-19
 * Interrupter 内部会启动一个线程等待超时后，去中断执行任务的线程
 */
public final class Interrupter {

    private static final AtomicLong counter = new AtomicLong(0);

    private final Thread threadToInterrupt;
    private Thread interrupterThread;
    private Clock time = new RealClock();

    private Interrupter(Thread threadToInterrupt) {
        this.threadToInterrupt = threadToInterrupt;
    }

    public static Interrupter interrupt(Thread thread) {
        return new Interrupter(thread);
    }

    Interrupter using(Clock time) {
        if (interrupterThread != null)
            throw new IllegalStateException("Controlling time after events have been put in motion will have no affect");
        this.time = time;
        return this;
    }

    public Interrupter after(final Duration duration) {
        final Timeout timeout = Timeout.timeout(duration, createAndStartStopWatch());
        interrupterThread = new Thread(new Runnable() {
            public void run() {
                try {
                    WaitFor.waitUntil(timeout); // 不停的睡眠-判断，睡眠-判断
                    if (!interrupterThread.isInterrupted()) {
                        Interrupter.this.threadToInterrupt.interrupt();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Interrupter-Thread-" + counter.incrementAndGet());
        interrupterThread.start();
        return this;
    }

    public void cancel() {
        if (interrupterThread.isAlive())
            interrupterThread.interrupt();
    }

    private StopWatch createAndStartStopWatch() {
        return new Timer(time);
    }

}
