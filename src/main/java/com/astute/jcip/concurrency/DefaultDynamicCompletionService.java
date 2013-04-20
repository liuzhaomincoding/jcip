package com.astute.jcip.concurrency;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Callable;

/**
 * User: 智深
 * Date: 13-4-20
 */
public class DefaultDynamicCompletionService<V> implements DynamicCompletionService<V> {

    private final Executor[] executors;
    private final AbstractExecutorService[] aess;
    private final BlockingQueue<Future<V>> completionQueue;

    private class QueueingFuture extends FutureTask<Void> {
        QueueingFuture(RunnableFuture<V> task) {
            super(task, null);
            this.task = task;
        }
        protected void done() { completionQueue.add(task); }
        private final Future<V> task;
    }

    private RunnableFuture<V> newTaskFor(Callable<V> task) {
        return new FutureTask<V>(task);
    }

    private RunnableFuture<V> newTaskFor(Runnable task, V result) {
        return new FutureTask<V>(task, result);
    }

    public DefaultDynamicCompletionService(List<java.util.concurrent.Executor> executors) {
        if(executors == null)
            throw new RuntimeException("executors shoud not be null!");
        if(executors.size() < 2)
            throw new RuntimeException("size of executors should be greater than 1!");
        this.executors = (Executor[]) executors.toArray();
        aess = new AbstractExecutorService[executors.size()];
        for(int i = 0; i < executors.size(); i++)
            aess[i] = (this.executors[i] instanceof AbstractExecutorService) ? (AbstractExecutorService) this.executors[i] : null;
        this.completionQueue = new LinkedBlockingQueue<Future<V>>();
    }

    @Override
    public List<V> submit(List<? extends Callable<V>> tasks) throws ExecutionException, TimeoutException {
        for(int i = 0; i < tasks.size(); i++)
            submit(tasks.get(i), this.executors[i]);

        return null;
    }

    public Future<V> submit(java.util.concurrent.Callable<V> task, java.util.concurrent.Executor executor) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<V> f = newTaskFor(task);
        executor.execute(new QueueingFuture(f));
        return f;
    }

    public Future<V> submit(Runnable task, V result, java.util.concurrent.Executor executor) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<V> f = newTaskFor(task, result);
        executor.execute(new QueueingFuture(f));
        return f;
    }

    public Future<V> take() throws InterruptedException {
        return completionQueue.take();
    }

    public Future<V> poll() {
        return completionQueue.poll();
    }

    public Future<V> poll(long timeout, TimeUnit unit)
            throws InterruptedException {
        return completionQueue.poll(timeout, unit);
    }

}
