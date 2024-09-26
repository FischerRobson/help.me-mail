package com.helpme.mail_ms.mail_ms.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailEventBuilder {
    private String receiver;
    private String ticketId;
    private String title;
    private String body;

    public EmailEventBuilder setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public EmailEventBuilder setTicketId(String ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public EmailEventBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EmailEventBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public EmailEventBuilder handleCreateTicket() {
        this.setTitle("Your Ticket is Updated");
        this.setBody("Your ticket has been updated with the latest information.");
        return this;
    }

    public EmailEventBuilder handleTicketUpdated() {
        this.setTitle("Your Ticket is Updated");
        this.setBody("Your ticket has been updated with the latest information.");
        return this;
    }

    public EmailEventBuilder handleTicketClosed() {
        this.setTitle("Your Ticket is Closed");
        this.setBody("Your ticket has been resolved and closed.");
        return this;
    }

    public EmailEventBuilder handleChatAdd() {
        this.setTitle("New Chat Message Added");
        this.setBody("A new message has been added to the ticket chat.");
        return this;
    }

    public Email build() {
        if (
                this.receiver.isEmpty() ||
                this.ticketId.isEmpty() ||
                this.title.isEmpty() ||
                this.body.isEmpty()) {
            throw new RuntimeException("Invalid email");
        }

        Email email = new Email();
        email.setReceiver(receiver);
        email.setTitle(title);
        email.setBody(body);
        return email;
    }

    public void sendEmail() {
        System.out.println("sendEmail");
    }
}
