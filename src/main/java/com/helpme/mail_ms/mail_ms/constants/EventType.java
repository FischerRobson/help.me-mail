package com.helpme.mail_ms.mail_ms.constants;

public enum EventType {
    TICKET_CREATED("TICKET_CREATED"),
    TICKET_UPDATED("TICKET_UPDATED"),
    TICKET_CLOSED("TICKET_CLOSED"),
    CHAT_ADD("CHAT_ADD");

    private final String eventType;

    EventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        return this.eventType;
    }
}
