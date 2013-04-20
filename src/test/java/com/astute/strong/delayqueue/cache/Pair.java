package com.astute.strong.delayqueue.cache;

/**
 * User: 智深
 * Date-Time: 13-4-19 - 上午2:21
 */
public class Pair<K, V> {
    public K first;

    public V second;

    public Pair() {
    }

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
}
