package org.crown.framework.consolelog;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.extern.slf4j.Slf4j;

/**
 * Create a blocking queue as a temporary carrier for logs output by the logging system
 *
 * @author Caratacus
 * @link https://cloud.tencent.com/developer/article/1096792
 */
@Slf4j
public class ConsoleLogQueue {

    /**
     * Queue size
     */
    public static final int QUEUE_MAX_SIZE = 10000;

    private static final ConsoleLogQueue alarmMessageQueue = new ConsoleLogQueue();
    /**
     * Blocking queue
     */
    private final BlockingQueue blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    private ConsoleLogQueue() {
    }

    public static ConsoleLogQueue getInstance() {
        return alarmMessageQueue;
    }

    /**
     * Message enqueue
     *
     * @param log
     * @return
     */
    public boolean push(ConsoleLog log) {
        return this.blockingQueue.add(log);
    }

    /**
     * Message deque
     *
     * @return
     */
    public ConsoleLog poll() {
        ConsoleLog result = null;
        try {
            result = (ConsoleLog) this.blockingQueue.take();
        } catch (InterruptedException e) {
            log.warn("Message dequeues abnormally:{}", e.getMessage());
        }
        return result;
    }
}
