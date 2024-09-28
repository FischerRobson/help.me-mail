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

    public static EventType fromString(String eventTypeStr) {
        for (EventType eventType : EventType.values()) {
            if (eventType.eventType.equalsIgnoreCase(eventTypeStr)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + eventTypeStr);
    }
}
