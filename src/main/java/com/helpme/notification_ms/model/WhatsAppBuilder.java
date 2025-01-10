package com.helpme.notification_ms.model;

import org.springframework.stereotype.Component;

@Component
public class WhatsAppBuilder {
    private String receiver;
    private String ticketId;
    private String message;

    public WhatsAppBuilder setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public WhatsAppBuilder setTicketId(String ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public WhatsAppBuilder handleCreateTicket() {
        this.message = "\uD83C\uDF1F *Your Ticket Has Been Created* \uD83C\uDF1F" +
                "\n" +
                "Hello, " + this.receiver +
                "\n" +
                "We have received your ticket and are currently reviewing it.    \n" +
                "\n" +
                "*Ticket:* " + this.ticketId + " \n" +
                "\n" +
                "We will notify you as soon as there is an update." +
                "\n" +
                "Best regards,  \n" +
                "HelpMe Support Team";
        return this;
    }

    public WhatsAppBuilder handleTicketUpdated() {
        this.message = "\uD83D\uDCE3 *Your Ticket Has Been Updated* \uD83D\uDCE3" +
                "\n" +
                "Hello, " + this.receiver +
                "\n" +
                "We have updated your ticket.    \n" +
                "\n" +
                "*Ticket:* " + this.ticketId + " \n" +
                "\n" +
                "Thank you for your patience." +
                "\n" +
                "Best regards,  \n" +
                "HelpMe Support Team";
        return this;
    }

    public WhatsAppBuilder handleTicketClosed() {
        this.message = "✅ *Your Ticket Has Been Closed* ✅" +
                "\n" +
                "Hello, " + this.receiver +
                "\n" +
                "We have resolved your ticket and it has been closed.    \n" +
                "\n" +
                "*Ticket:* " + this.ticketId + " \n" +
                "\n" +
                "Best regards,  \n" +
                "HelpMe Support Team";
        return this;
    }

    public WhatsAppBuilder handleChatAdd() {
        this.message = "\uD83D\uDCAC *New Chat Message Added* \uD83D\uDCAC\n" +
                "\n" +
                "Hello, " + this.receiver +
                "\n" +
                "A new message has been added to your ticket chat.  \n" +
                "\n" +
                "*Ticket:* " + this.ticketId + " \n" +
                "\n" +
                "You can reply at your earliest convenience.  \n" +
                "\n" +
                "Best regards,  \n" +
                "HelpMe Support Team";
        return this;
    }

    public WhatsApp build() {
        if (
                this.receiver.isEmpty() ||
                        this.ticketId.isEmpty()) {
            throw new RuntimeException("Invalid WhatsApp");
        }

        WhatsApp whatsApp = new WhatsApp();
        whatsApp.setMessage(this.message);
        return whatsApp;
    }
}
