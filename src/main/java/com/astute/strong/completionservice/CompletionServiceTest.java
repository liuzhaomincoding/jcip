package com.astute.strong.completionservice;

import java.util.concurrent.*;

/**
 * User: 智深
 * Date-Time: 13-4-19 - 下午8:25
 */
public class CompletionServiceTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        // 创建 CompletionService
        CompletionService<String> serv = new ExecutorCompletionService<String>(exec);
        for (int index = 0; index < 5; index++) {
            final int NO = index;
            // Callable 接口类似于 Runnable
            Callable<String> downImg = new Callable<String>() {
                public String call() throws Exception {
                    Thread.sleep((long) (Math.random() * 10000));
                    return "Downloaded Image " + NO;
                }
            };
            serv.submit(downImg);
        }

        Thread.sleep(1000 * 2);

        System.out.println("Show web content");

        for (int index = 0; index < 5; index++) {
            Future<String> task = serv.take();
            String img = task.get();
            System.out.println(img);
        }

        System.out.println("End");

        exec.shutdown();
    }

}
