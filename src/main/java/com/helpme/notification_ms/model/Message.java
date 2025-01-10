package com.helpme.notification_ms.model;

import com.helpme.notification_ms.constants.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    private NotificationKinds kind;
    private String receiver;
    private String ticketId;
    private EventType eventType;
}
