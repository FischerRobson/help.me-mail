package com.helpme.notification_ms.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricsService {
    private final Counter messagesProcessed;
    private final Counter messageFailures;

    public MetricsService(MeterRegistry meterRegistry) {
        this.messagesProcessed = meterRegistry.counter("messages_processed_total");
        this.messageFailures = meterRegistry.counter("message_failures_total");
    }

    public void incrementMessagesProcessed() {
        messagesProcessed.increment();
    }

    public void incrementMessageFailures() {
        messageFailures.increment();
    }
}
