package com.astute.jcip.concurrency;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface TimeoutableCompletionService {
    <T> List<T> submit(List<? extends java.util.concurrent.Callable<T>> tasks) throws ExecutionException, TimeoutException;
}
