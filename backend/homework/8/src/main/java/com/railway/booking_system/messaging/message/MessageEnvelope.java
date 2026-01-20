package com.railway.booking_system.messaging.message;

public class MessageEnvelope {
    public final String type;
    public final Object payload;

    public MessageEnvelope(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
