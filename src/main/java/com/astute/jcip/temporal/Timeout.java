package com.astute.jcip.temporal;


/**
 * A class to represent a timeout. You can ask if the timeout {@link #hasExpired()} but bear in mind that the {@link StopWatch}
 * used internally may start on construction of the {@link com.astute.jcip.temporal.Timeout} instance. You should therefore new up a {@link com.astute.jcip.temporal.Timeout}
 * when you actually want the timeout to start ticking down and not keep instances hanging around.
 * 表示超时；
 */
public final class Timeout {

    private Duration duration;
    private StopWatch timer;

    /** @since 1.1 */
    public static Timeout timeout(Duration duration) {
        return new Timeout(duration);
    }

    /** @since 1.1 */
    public static Timeout timeout(Duration duration, StopWatch stopWatch) {
        return new Timeout(duration, stopWatch);
    }

    // 秒表创建时，当前时间已经被作为测试初始时间
    private Timeout(Duration duration) {
        this(duration, startStopWatch());
    }

    private Timeout(Duration duration, StopWatch timer) {
        if (duration.inMillis() <= 0)
            throw new IllegalArgumentException();
        this.duration = duration;
        this.timer = timer;
    }

    public boolean hasExpired() {
        timer.lap();
        // 判断秒表经过的时间段是不是比'设定的时间段'长，长说明过期超时了
        return timer.elapsedTime().greaterThan(duration);
    }

    private static Timer startStopWatch() {
        return new Timer(new RealClock());
    }
}