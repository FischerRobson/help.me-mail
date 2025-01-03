package com.helpme.mail_ms.mail_ms.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageBuilder {

    @Autowired
    ObjectMapper objectMapper;

    public Message parse(String rawMessage) {
        try {
            return objectMapper.readValue(rawMessage, Message.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse message: " + rawMessage, e);
        }
    }


}
