package com.railway.booking_system.messaging.broker;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Topic {
    private final String name;
    private final CopyOnWriteArrayList<Consumer<Object>> subscribers = new CopyOnWriteArrayList<>();

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void subscribe(Consumer<Object> handler) {
        subscribers.add(handler);
    }

    public void unsubscribe(Consumer<Object> handler) {
        subscribers.remove(handler);
    }

    public CopyOnWriteArrayList<Consumer<Object>> getSubscribers() {
        return subscribers;
    }
}
