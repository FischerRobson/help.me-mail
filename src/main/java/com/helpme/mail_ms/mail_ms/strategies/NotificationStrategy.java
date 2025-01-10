package com.helpme.mail_ms.mail_ms.strategies;

import com.helpme.mail_ms.mail_ms.model.Message;
import com.helpme.mail_ms.mail_ms.model.NotificationKinds;
import com.helpme.mail_ms.mail_ms.model.NotificationService;
import com.helpme.mail_ms.mail_ms.services.EmailService;
import com.helpme.mail_ms.mail_ms.services.WhatsAppService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class NotificationStrategy {

    @Autowired
    private EmailService emailService;

    @Autowired
    private WhatsAppService whatsAppService;

    private final HashMap<NotificationKinds, NotificationService> strategies = new HashMap<>();

    @PostConstruct
    public void initializeStrategies() {
        strategies.put(NotificationKinds.EMAIL, emailService);
        strategies.put(NotificationKinds.WHATSAPP, whatsAppService);
    }

    public NotificationService getNotificationStrategy(Message message) {
        NotificationService service = this.strategies.get(message.getKind());
        if(service == null) {
            throw new IllegalArgumentException("No strategy found for kind: " + message.getKind());
        }
        return service;
    }

}
