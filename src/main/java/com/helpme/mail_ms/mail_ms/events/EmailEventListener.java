package com.helpme.mail_ms.mail_ms.events;

import com.helpme.mail_ms.mail_ms.constants.Constants;
import com.helpme.mail_ms.mail_ms.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailEventListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private Constants constants;

    @RabbitListener(queues = "#{constants.EMAIL_QUEUE}")
    public void listen(String message) {
       String[] parts = message.split("\\|");
       String to = parts[0];
       String subject = parts[1];
       String body = parts[2];

        emailService.sendSimpleEmail(to, subject, body);
    }

}