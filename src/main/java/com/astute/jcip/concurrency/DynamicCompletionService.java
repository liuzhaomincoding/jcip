package com.astute.jcip.concurrency;

import java.util.List;
import java.util.concurrent.*;

/**
 * User: 智深
 * Date: 13-4-20
 */
public interface DynamicCompletionService<V> {

    List<V> submit(List<? extends java.util.concurrent.Callable<V>> tasks)
            throws ExecutionException, TimeoutException;

    Future<V> take() throws InterruptedException;

    Future<V> poll();

    Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException;
}
