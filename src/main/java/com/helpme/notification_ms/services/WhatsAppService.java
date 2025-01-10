package com.helpme.notification_ms.services;

import com.helpme.notification_ms.model.Message;
import com.helpme.notification_ms.model.NotificationService;
import com.helpme.notification_ms.model.WhatsApp;
import com.helpme.notification_ms.model.WhatsAppBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);

    @Autowired
    WhatsAppBuilder whatsAppBuilder;

    public void sendNotification(Message message) {

        this.whatsAppBuilder.setReceiver(message.getReceiver()).setTicketId(message.getTicketId());

        // extract to builder
        switch (message.getEventType()) {
            case TICKET_CREATED:
                whatsAppBuilder.handleCreateTicket();
                break;
            case TICKET_UPDATED:
                whatsAppBuilder.handleTicketUpdated();
                break;
            case TICKET_CLOSED:
                whatsAppBuilder.handleTicketClosed();
                break;
            case CHAT_ADD:
                whatsAppBuilder.handleChatAdd();
                break;
        }

        WhatsApp whatsApp = this.whatsAppBuilder.build();

        // To implement whats app feature.

    }
}
