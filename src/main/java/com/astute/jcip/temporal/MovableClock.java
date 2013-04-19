package com.astute.jcip.temporal;

import java.util.Date;

/**
 * User: 智深
 * Date: 13-4-19
 */
public final class MovableClock implements Clock {

    private final Date current;

    public MovableClock() {
        current = new Date(0); // 默认值 1970.1.1 00:00:00
    }

    public MovableClock(Date date) {
        current = new Date(date.getTime());
    }

    @Override
    public Date now() {
        return new Date(current.getTime());
    }

    public void setTime(Duration time) {
        current.setTime(time.inMillis());
    }

    public void incrementBy(Duration time) {
        current.setTime(current.getTime() + time.inMillis());
    }

}
