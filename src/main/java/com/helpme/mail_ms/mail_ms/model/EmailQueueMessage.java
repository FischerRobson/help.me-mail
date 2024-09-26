package com.helpme.mail_ms.mail_ms.model;

import com.helpme.mail_ms.mail_ms.constants.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailQueueMessage {
    private EventType eventType;
    private String emailReceiver;
    private String ticketId;

    @Override
    public String toString() {
        return "QueueMessage{" +
                "eventType='" + eventType + '\'' +
                ", emailReceiver='" + emailReceiver + '\'' +
                ", ticketId='" + ticketId + '\'' +
                '}';
    }
}
