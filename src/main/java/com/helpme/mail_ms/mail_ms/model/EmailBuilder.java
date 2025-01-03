package com.helpme.mail_ms.mail_ms.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailBuilder {
    private String receiver;
    private String ticketId;
    private String title;
    private String body;


    public EmailBuilder setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public EmailBuilder setTicketId(String ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    private EmailBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    private EmailBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public EmailBuilder handleCreateTicket() {
        this.setTitle("Your Ticket is Created");
        this.setBody("Your ticket has been created and and will soon be processed.");
        return this;
    }

    public EmailBuilder handleTicketUpdated() {
        this.setTitle("Your Ticket is Updated");
        this.setBody("Your ticket has been updated with the latest information.");
        return this;
    }

    public EmailBuilder handleTicketClosed() {
        this.setTitle("Your Ticket is Closed");
        this.setBody("Your ticket has been resolved and closed.");
        return this;
    }

    public EmailBuilder handleChatAdd() {
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
        Map<String, Object> vars = new HashMap<>();
        vars.put("title", this.title);
        vars.put("body", this.body);
        email.setVariables(vars);

        return email;
    }
}
