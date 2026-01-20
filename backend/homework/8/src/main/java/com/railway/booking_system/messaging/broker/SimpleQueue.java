package com.railway.booking_system.messaging.broker;

import org.springframework.stereotype.Component;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.function.Consumer;

@Component
public class SimpleQueue {
    private final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();
    private final ExecutorService worker;

    public SimpleQueue() {
        this.worker = Executors.newSingleThreadExecutor();
    }

    public void push(Object msg) {
        queue.add(msg);
    }

    public void start(Consumer<Object> handler) {
        worker.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Object msg = queue.take();
                    try {
                        handler.accept(msg);
                    } catch (Exception ex) {
                        // log and continue (in prod: dead-letter or retry)
                        System.err.println("[SimpleQueue] handler error: " + ex.getMessage());
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @PreDestroy
    public void stop() {
        worker.shutdownNow();
    }
}
