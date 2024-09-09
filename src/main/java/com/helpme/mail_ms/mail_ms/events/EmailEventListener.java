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
        String to = extractToFromMessage(message);
        String subject = extractSubjectFromMessage(message);
        String body = extractBodyFromMessage(message);

        emailService.sendSimpleEmail(to, subject, body);
    }

    private String extractToFromMessage(String message) {
        return "recipient@example.com"; // Example
    }

    private String extractSubjectFromMessage(String message) {
        return "Email Subject"; // Example
    }

    private String extractBodyFromMessage(String message) {
        return "Email body text"; // Example
    }
}