package com.astute.jcip.concurrency;

import com.astute.jcip.temporal.Clock;
import com.astute.jcip.temporal.Duration;
import com.astute.jcip.temporal.RealClock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.*;

import java.lang.Thread;

/**
 * User: 智深
 * Date: 13-4-19
 */
public class DefaultTimeoutableCompletionService implements TimeoutableCompletionService {

    private static final boolean INTERRUPT_IF_RUNNING = true;
    private static final Duration DEFAULT_TIMEOUT = Duration.seconds(30);

    private final CompletionService completionService;
    private final Duration timeout;
    private final Clock time;

    public DefaultTimeoutableCompletionService(CompletionService completionService) {
        this(completionService, DEFAULT_TIMEOUT, new RealClock());
    }

    public DefaultTimeoutableCompletionService(CompletionService completionService, Duration timeout) {
        this(completionService, timeout, new RealClock());
    }

    public DefaultTimeoutableCompletionService(CompletionService completionService, Duration timeout, Clock time) {
        this.timeout = timeout;
        this.time = time;
        this.completionService = completionService;
    }

    public <T> List<T> submit(List<? extends Callable<T>> tasks) throws ExecutionException, TimeoutException {
        List<Future<T>> submitted = new ArrayList<Future<T>>();
        try {
            for (Callable task : tasks) {
                submitted.add(completionService.submit(task));
            }
            return waitFor(tasks.size(), timeout);
        } finally {
            for (Future<T> future : submitted) {
                future.cancel(INTERRUPT_IF_RUNNING);
            }
        }
    }

    private <T> List<T> waitFor(int tasks, Duration timeout) throws ExecutionException, TimeoutException {
        List<T> completed = new ArrayList<T>();
        Interrupter interrupter = Interrupter.interrupt(Thread.currentThread()).using(time).after(timeout);
        try {
            for (int i = 0; i < tasks; i++) {
                try {
                    Future<T> future = completionService.take();
                    completed.add(future.get());
                } catch (InterruptedException e) {
                    throw new TimeoutExceptionWithResults("timed out after " + timeout.toString(), completed);
                }
            }
        } finally {
            interrupter.cancel();
        }
        return completed;
    }
}
