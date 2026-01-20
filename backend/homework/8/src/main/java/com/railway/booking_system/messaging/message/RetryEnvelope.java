package com.railway.booking_system.messaging.message;

public class RetryEnvelope<T> {
    private final T payload;
    private int attempts;

    public RetryEnvelope(T payload) {
        this.payload = payload;
        this.attempts = 0;
    }

    public T getPayload() {
        return payload;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        this.attempts++;
    }
}
