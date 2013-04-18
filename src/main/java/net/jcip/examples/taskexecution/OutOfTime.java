package net.jcip.examples.taskexecution;

import java.util.*;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * OutOfTime
 * Timer 并不处理异常，直接退出
 * 其他缺陷：单线程
 */

public class OutOfTime {
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask {
        public void run() {
            throw new RuntimeException();
        }
    }
}
