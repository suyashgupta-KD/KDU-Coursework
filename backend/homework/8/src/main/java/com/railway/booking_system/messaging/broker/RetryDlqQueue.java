package com.railway.booking_system.messaging.broker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class RetryDlqQueue<T> {

    private final String name;
    private final BlockingQueue<T> mainQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<T> dlq = new LinkedBlockingQueue<>();

    private final int maxRetries;
    private final long delayMillis;

    public RetryDlqQueue(String name, int maxRetries, long delayMillis) {
        this.name = name;
        this.maxRetries = maxRetries;
        this.delayMillis = delayMillis;
    }

    public void push(T message) {
        mainQueue.add(message);
    }

    public void start(Consumer<T> handler) {
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                T msg;
                try {
                    msg = mainQueue.take(); // blocks until message exists
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                boolean success = false;

                for (int attempt = 1; attempt <= maxRetries; attempt++) {
                    try {
                        handler.accept(msg);
                        success = true;
                        break;
                    } catch (Exception ex) {
                        System.out.println("[" + name + "] attempt " + attempt + "/" + maxRetries +
                                " failed: " + ex.getMessage());

                        if (attempt < maxRetries) {
                            try {
                                Thread.sleep(delayMillis);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                    }
                }

                if (!success) {
                    dlq.add(msg);
                    System.out.println("[" + name + "] moved to DLQ: booking-error-queue");
                }
            }
        });

        worker.setName(name + "-worker");
        worker.start();
    }

    // for testing
    public int dlqSize() {
        return dlq.size();
    }

    public T pollDlq() {
        return dlq.poll();
    }
}
