package com.helpme.mail_ms.mail_ms.events;

import com.helpme.mail_ms.mail_ms.constants.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Constants constants;

    public void sendEmailEvent(String message) {
        rabbitTemplate.convertAndSend(constants.EMAIL_QUEUE, message);
    }
}
