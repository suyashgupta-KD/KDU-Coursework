package com.railway.booking_system.messaging.broker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class ManualAckQueue<T> {

    public interface MessageHandle<T> {
        T payload();

        void ack();
    }

    private static class InFlight<T> implements MessageHandle<T> {
        private final T payload;
        private final Runnable ackFn;
        private volatile boolean acked = false;

        InFlight(T payload, Runnable ackFn) {
            this.payload = payload;
            this.ackFn = ackFn;
        }

        @Override
        public T payload() {
            return payload;
        }

        @Override
        public void ack() {
            if (!acked) {
                acked = true;
                ackFn.run();
            }
        }
    }

    private final String name;
    private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();

    public ManualAckQueue(String name) {
        this.name = name;
    }

    public void push(T msg) {
        queue.add(msg);
    }

    /**
     * At-least-once semantics:
     * - Consumer must call handle.ack() on success
     * - If consumer throws or exits before ack, message is requeued
     */
    public void start(Consumer<MessageHandle<T>> consumer) {
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                T msg;
                try {
                    msg = queue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                // Ack removes from system; "unacked" -> requeue on failure
                InFlight<T> handle = new InFlight<>(msg, () -> {
                    // no-op here because we already removed it from queue by taking it
                    // ack simply marks processed successfully
                });

                try {
                    consumer.accept(handle);

                    // If consumer forgets to ack, treat as failure (redeliver)
                    if (!handle.acked) {
                        System.out.println("[" + name + "] not acked -> redelivering");
                        queue.add(msg);
                    }
                } catch (Exception ex) {
                    System.out.println("[" + name + "] consumer crashed: " + ex.getMessage() + " -> redelivering");
                    queue.add(msg); // redelivery
                }
            }
        });

        worker.setName(name + "-worker");
        worker.start();
    }
}
