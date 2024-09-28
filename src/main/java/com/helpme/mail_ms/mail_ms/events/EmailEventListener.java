package com.helpme.mail_ms.mail_ms.events;

import com.helpme.mail_ms.mail_ms.constants.Constants;
import com.helpme.mail_ms.mail_ms.constants.EventType;
import com.helpme.mail_ms.mail_ms.model.Email;
import com.helpme.mail_ms.mail_ms.model.EmailEventBuilder;
import com.helpme.mail_ms.mail_ms.services.EmailService;
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
    EmailEventBuilder emailEventBuilder;

    private static final Logger logger = LoggerFactory.getLogger(EmailEventListener.class);

    @RabbitListener(queues = "#{constants.EMAIL_QUEUE}")
    public void listen(String message) throws MessagingException {

        logger.info("Reading message from queue...");
        String[] parts = message.split("\\|");
        String ticketId = parts[0];
        String to = parts[1];
        String event = parts[2];

        emailEventBuilder.setTicketId(ticketId).setReceiver(to);

        try {
            EventType eventType = EventType.fromString(event);

            switch (eventType) {
                case TICKET_CREATED:
                    emailEventBuilder.handleCreateTicket();
                    break;
                case TICKET_UPDATED:
                    emailEventBuilder.handleTicketUpdated();
                    break;
                case TICKET_CLOSED:
                    emailEventBuilder.handleTicketClosed();
                    break;
                case CHAT_ADD:
                    emailEventBuilder.handleChatAdd();
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown event type: " + event);
        }

        Email email = emailEventBuilder.build();

        try {
            emailService.sendSimpleEmail(email);
        } catch (Exception e) {
            logger.info("Failed to sent email to " + to + " | ticket " + ticketId);
        }
    }

}