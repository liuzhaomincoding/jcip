package com.astute.jcip.temporal;

/**
 * 秒表定义
 * 秒表的实现应该记录 lap 调用和生成秒表时刻的时间差值。允许多次调用lap；不应在reset调用之前调用lap，可能会导致负的差值
 * 秒表构建时就会启动，也可以提供一个可选的方法启动
 * @since 1.2
 */
public interface StopWatch {

    /**
     * 秒表复位，lap 方法调用之前调用
     */
    void reset();

    /**
     * 向前移动秒表，在 elapsedTime 方法被调用之前，可以调用多次
     */
    void lap();

    /**
     * 返回 lap 之后的时间和构造时刻的时间差值
     */
    Duration elapsedTime();
}
