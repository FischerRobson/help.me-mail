package com.helpme.mail_ms.mail_ms.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    @Value("${rabbitmq.queue.email}")
    public String EMAIL_QUEUE;
}
