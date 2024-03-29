package com.astute.jcip.temporal;

import java.util.concurrent.TimeUnit;

/**
 * User: 智深
 * Date-Time: 13-4-19
 * Description: 表示持续时间，时间段
 */
public class Duration implements Comparable<Duration> {

    private final Long value;
    private final TimeUnit unit;

    private Duration(Long value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static Duration seconds(long seconds) {
        validate(seconds, TimeUnit.SECONDS);
        return new Duration(seconds, TimeUnit.SECONDS);
    }

    public static Duration millis(long millis) {
        validate(millis, TimeUnit.MILLISECONDS);
        return new Duration(millis, TimeUnit.MILLISECONDS);
    }

    public static Duration minutes(long minutes) {
        long seconds = minutes * 60;
        validate(seconds,  TimeUnit.SECONDS);
        return new Duration(seconds, TimeUnit.SECONDS);
    }

    public static Duration hours(long hours) {
        return minutes(hours * 60);
    }

    public static Duration days(long days) {
        return hours(days * 24);
    }

    // 验证 value 的值，必须小于 Long 所能表示的最大值
    private static void validate(long value, TimeUnit unit) {
        Duration duration = new Duration(value, unit);
        if (duration.inMillis() == Long.MAX_VALUE)
            throw new IllegalArgumentException();
    }

    public long inMillis() {
        return unit.toMillis(value);
    }

    public long inSeconds() {
        return unit.toSeconds(value);
    }

    public long inMinutes() {
        return unit.toSeconds(value) / 60;
    }

    public long inHours() {
        return inMinutes() / 60;
    }

    public long inDays() {
        return inHours() / 24;
    }

    public Duration plus(Duration duration) {
        return millis(duration.inMillis() + this.inMillis());
    }

    public Duration minus(Duration duration) {
        return millis(this.inMillis() - duration.inMillis());
    }

    public Boolean greaterThan(Duration duration) {
        return this.inMillis() > duration.inMillis();
    }

    public Boolean lessThan(Duration duration) {
        return this.inMillis() < duration.inMillis();
    }

    @Override
    public int hashCode() {
        return new Long(unit.toMillis(value)).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o.getClass().getName().equals(Duration.class.getName())))
            return false;
        Duration other = (Duration) o;
        return other.unit.toMillis(other.value) == this.unit.toMillis(this.value);
    }

    public String toString() {
        return "Duration " + value + " " + unit;
    }

    public int compareTo(Duration other) {
        return this.value.compareTo(other.value);
    }
}
