package com.astute.strong.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * User: 智深
 * Date-Time: 13-4-18 - 下午9:33
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap();
        Object val = map.putIfAbsent("abc", 3);
        System.out.println("==========");

    }

}
