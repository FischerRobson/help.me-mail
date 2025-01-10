package com.helpme.notification_ms.services;

import com.helpme.notification_ms.model.Email;
import com.helpme.notification_ms.model.EmailBuilder;
import com.helpme.notification_ms.model.Message;
import com.helpme.notification_ms.model.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService implements NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    EmailBuilder emailBuilder;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendNotification(Message message) {

        emailBuilder.setTicketId(message.getTicketId()).setReceiver(message.getReceiver());

        switch (message.getEventType()) {
            case TICKET_CREATED:
                emailBuilder.handleCreateTicket();
                break;
            case TICKET_UPDATED:
                emailBuilder.handleTicketUpdated();
                break;
            case TICKET_CLOSED:
                emailBuilder.handleTicketClosed();
                break;
            case CHAT_ADD:
                emailBuilder.handleChatAdd();
                break;
        }

        Email email = this.emailBuilder.build();

        Context context = new Context();
        context.setVariables(email.getVariables());

        String emailContent = templateEngine.process("email-template", context);

       try {
           MimeMessage mimeMessage = mailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

           helper.setTo(email.getReceiver());
           helper.setSubject(email.getVariables().get("title").toString());
           helper.setText(emailContent, true);

           mailSender.send(mimeMessage);
           logger.info("Email sent to {} with success!", email.getReceiver());
       } catch (MessagingException e) {
           logger.error("Error on sent email", e);
       }
    }

}