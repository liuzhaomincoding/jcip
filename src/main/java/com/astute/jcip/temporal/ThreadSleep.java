package com.astute.jcip.temporal;

/**
 * 线程睡眠，调用sleep方法的线程睡眠 period 时间段
 */
public class ThreadSleep implements Sleeper {

    private final Duration period;

    public ThreadSleep(Duration period) {
        this.period = period;
    }

    public void sleep() throws InterruptedException {
        Thread.sleep(period.inMillis());
    }
}
