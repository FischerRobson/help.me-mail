package com.helpme.mail_ms.mail_ms.events;

import com.helpme.mail_ms.mail_ms.constants.Constants;
import com.helpme.mail_ms.mail_ms.model.*;
import com.helpme.mail_ms.mail_ms.services.EmailService;
import com.helpme.mail_ms.mail_ms.services.WhatsAppService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private Constants constants;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private MessageBuilder messageBuilder;

    private static final Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);

    @RabbitListener(queues = "#{constants.NOTIFICATION_QUEUE}")
    public void listen(String rawMessage) throws MessagingException {

        logger.info("Reading message from queue...");

        Message message = null;

        try {
            message = messageBuilder.parse(rawMessage);
        } catch (IllegalArgumentException exception) {
            logger.error("Failed to parse message: {}", rawMessage);
            return;
        }

        NotificationService service = null;

        if(message.getKind().equals("WHATSAPP")) {
            service = whatsAppService;
        } else if(message.getKind().equals("EMAIL")) {
            service = emailService;
        }

        if(service == null) {
            throw new RuntimeException("Invalid kind of service");
        }

        try {
            service.sendNotification(message);
        } catch (Exception e) {
            logger.warn("Failed to notify to {} | ticket {}", message.getReceiver(), message.getTicketId());
        }
    }

}