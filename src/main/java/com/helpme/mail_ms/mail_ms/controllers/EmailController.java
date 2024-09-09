package com.helpme.mail_ms.mail_ms.controllers;

import com.helpme.mail_ms.mail_ms.events.EmailEventPublisher;
import com.helpme.mail_ms.mail_ms.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailEventPublisher emailEventPublisher;

    @Autowired
    EmailService emailService;

    @GetMapping
    public void sendEmail() {
//        emailEventPublisher.sendEmailEvent(
//                "recipient@example.com|" +
//                "Ticket Update: Your Ticket #12345|" +
//                "Dear Customer, your support ticket #12345 has been updated. Please check your account for more details."
//        );

        emailService.sendSimpleEmail("fischerrobson@gmail.com", "TEST", "Hello stranger");
    }

}
