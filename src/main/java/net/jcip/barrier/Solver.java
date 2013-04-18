package net.jcip.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * User: 智深
 * Date-Time: 13-4-17 - 下午10:41
 * CyclicBarrier 官方文档中摘录的例子
 */
public class Solver {
    final int N;
    final float[][] data;
    final CyclicBarrier barrier;

    class Worker implements Runnable {
        int myRow;

        Worker(int row) {
            myRow = row;
        }

        public void run() {
            // while (!done()) {
            // processRow(myRow);
            while (true) {
                try {
                    barrier.await();
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            }
        }
    }

    public Solver(float[][] matrix) {
        data = matrix;
        N = matrix.length;
        barrier = new CyclicBarrier(N,
                new Runnable() {
                    public void run() {
                        // mergeRows(...);
                    }
                });
        for (int i = 0; i < N; ++i)
            new Thread(new Worker(i)).start();

        //waitUntilDone();
    }
}