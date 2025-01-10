package com.helpme.mail_ms.mail_ms.services;

import com.helpme.mail_ms.mail_ms.rabbitmq.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DLQRetryService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    private static final Logger logger = LoggerFactory.getLogger(DLQRetryService.class);

    @Autowired
    public DLQRetryService(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    @Scheduled(fixedRate = 10000) // Retry every 10 seconds
    public void retryMessages() {
        logger.info("Starting to retry sending messages from DLQ");
        String dlqQueue = rabbitMQConfig.getDlqQueue();

        Message message = rabbitTemplate.receive(dlqQueue);
        while (message != null) {
            try {
                int retryCount = getRetryCount(message);

                if (retryCount < 3) {
                    logger.info("Retrying message (Attempt {}): {}", retryCount + 1, new String(message.getBody()));

                    incrementRetryCount(message);
                } else {
                    logger.warn("Discarding message after 3 attempts: {}", new String(message.getBody()));
                }
            } catch (Exception e) {
                logger.error("Error processing message from DLQ: {}", new String(message.getBody()), e);
            }

            // Get the next message from the DLQ
            message = rabbitTemplate.receive(dlqQueue);
        }
    }


    private void incrementRetryCount(Message message) {
        int retryCount = getRetryCount(message);

        // Create new message properties to include the incremented retry count
        MessageProperties newProperties = new MessageProperties();
        newProperties.getHeaders().putAll(message.getMessageProperties().getHeaders());
        newProperties.setHeader("x-retry-count", retryCount + 1);

        // Create a new message with the updated properties
        byte[] body = message.getBody();
        Message updatedMessage = new Message(body, newProperties);

        // Send the updated message
        rabbitTemplate.send(RabbitMQConfig.MAIN_EXCHANGE, rabbitMQConfig.getNotificationQueue(), updatedMessage);
    }

    private int getRetryCount(Message message) {
        Object header = message.getMessageProperties().getHeaders().get("x-retry-count");
        return header == null ? 0 : (int) header;
    }
}