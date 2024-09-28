package com.helpme.mail_ms.mail_ms.services;

import com.helpme.mail_ms.mail_ms.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendSimpleEmail(Email email) throws MessagingException {

        Context context = new Context();
        context.setVariables(email.getVariables());

        String emailContent = templateEngine.process("email-template", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email.getReceiver());
        helper.setSubject(email.getVariables().get("title").toString());
        helper.setText(emailContent, true);

        mailSender.send(message);
    }
}