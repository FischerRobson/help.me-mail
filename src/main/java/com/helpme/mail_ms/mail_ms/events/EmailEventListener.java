package com.helpme.mail_ms.mail_ms.events;

import com.helpme.mail_ms.mail_ms.constants.Constants;
import com.helpme.mail_ms.mail_ms.constants.EventType;
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
public class EmailEventListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private Constants constants;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private MessageBuilder messageBuilder;

    private static final Logger logger = LoggerFactory.getLogger(EmailEventListener.class);

    @RabbitListener(queues = "#{constants.EMAIL_QUEUE}")
    public void listen(String rawMessage) throws MessagingException {

        logger.info("Reading message from queue...");
        Message message = messageBuilder.parse(rawMessage);

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
            logger.warn("Failed to sent email to {} | ticket {}", message.getReceiver(), message.getTicketId());
        }
    }

}