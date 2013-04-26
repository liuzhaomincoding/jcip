package com.astute.strong.newtaskfor;

import java.util.concurrent.*;

/**
 * User: 智深
 * Date: 13-4-25
 */
public class ExecutorService extends ThreadPoolExecutor {

    public ExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return super.newTaskFor(callable);
    }

}
