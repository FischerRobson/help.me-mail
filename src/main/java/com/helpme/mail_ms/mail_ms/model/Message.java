package com.helpme.mail_ms.mail_ms.model;

import com.helpme.mail_ms.mail_ms.constants.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    private String kind;
    private String receiver;
    private String ticketId;
    private EventType eventType;
}
