/*
 * Copyright (c) 2009-2012, toby weston & tempus-fugit committers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.astute.jcip.concurrency;

import com.astute.jcip.condition.Conditions;
import com.astute.jcip.temporal.Duration;
import com.astute.jcip.temporal.Timeout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;

import static com.astute.jcip.concurrency.ThreadUtils.resetInterruptFlagWhen;
import static com.astute.jcip.temporal.WaitFor.waitOrTimeout;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public final class ExecutorServiceShutdown {

    private final ExecutorService executor;

    private ExecutorServiceShutdown(ExecutorService executor) {
        this.executor = executor;
    }

    public static ExecutorServiceShutdown shutdown(ExecutorService executor) {
        return new ExecutorServiceShutdown(executor);
    }

    public Boolean waitingForCompletion(final Duration duration) {
        if (executor == null)
            return false;
        executor.shutdown();
        return resetInterruptFlagWhen(awaitingTerminationIsInterrupted(duration));
    }

    /** @since 1.1 */
    public Boolean waitingForShutdown(Timeout timeout) throws TimeoutException, InterruptedException {
        if (executor == null)
            return false;
        executor.shutdownNow();
        waitOrTimeout(Conditions.shutdown(executor), timeout);
        return true;
    }

    private Interruptible<Boolean> awaitingTerminationIsInterrupted(final Duration timeout) {
        return new Interruptible<Boolean>(){
            public Boolean call() throws InterruptedException {
                return executor.awaitTermination(timeout.inMillis(), MILLISECONDS);
            }
        };
    }

}
