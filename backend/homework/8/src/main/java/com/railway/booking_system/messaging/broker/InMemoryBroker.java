package com.railway.booking_system.messaging.broker;

import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class InMemoryBroker {
    private final Map<String, Topic> topics = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors()));

    public Topic getOrCreateTopic(String name) {
        return topics.computeIfAbsent(name, Topic::new);
    }

    /**
     * Publish an event to a topic — each subscriber receives an independent async
     * task.
     * This method returns quickly; publishing is non-blocking.
     */
    public void publish(String topicName, Object message) {
        Topic t = getOrCreateTopic(topicName);
        for (var subscriber : t.getSubscribers()) {
            executor.submit(() -> {
                try {
                    subscriber.accept(message);
                } catch (Exception ex) {
                    // log & ignore so one subscriber failure does not affect others
                    System.err.println("[Broker] subscriber error on topic " + topicName + ": " + ex.getMessage());
                }
            });
        }
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}
