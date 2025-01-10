package com.helpme.notification_ms.events;

import com.helpme.mail_ms.mail_ms.model.*;
import com.helpme.notification_ms.model.Message;
import com.helpme.notification_ms.model.MessageBuilder;
import com.helpme.notification_ms.model.NotificationService;
import com.helpme.notification_ms.rabbitmq.RabbitMQConfig;
import com.helpme.notification_ms.strategies.NotificationStrategy;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NotificationEventListener implements DisposableBean {

    @Autowired
    private MessageBuilder messageBuilder;

    @Autowired
    private NotificationStrategy notificationStrategy;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    private ExecutorService executorService;

    private static final Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);

    @PostConstruct
    public void initializeExecutor() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @RabbitListener(queues = "#{rabbitMQConfig.getNotificationQueue()}")
    public void listen(String rawMessage) {

        logger.info("Reading message from notification queue");

        executorService.submit(() -> {
            Message message = null;

            try {
                message = messageBuilder.parse(rawMessage);
            } catch (IllegalArgumentException exception) {
                logger.error("Failed to parse message: {}", rawMessage);
               // rabbitTemplate.convertAndSend(RabbitMQConfig.DLX_EXCHANGE, rabbitMQConfig.getDlqQueue(), rawMessage);
                sendToDlq(rawMessage);
                return;
            }

            NotificationService service = notificationStrategy.getNotificationStrategy(message);

//            try {
//                service.sendNotification(message);
//                logger.info("Successfully sent notification to {} for ticket {}", message.getReceiver(), message.getTicketId());
//            } catch (Exception e) {
//                logger.error("Failed to notify to {} | ticket {}", message.getReceiver(), message.getTicketId(), e);
//                rabbitTemplate.convertAndSend(RabbitMQConfig.DLX_EXCHANGE, rabbitMQConfig.getDlqQueue(), rawMessage);
//            }
        });
    }

    private void sendToDlq(String rawMessage) {
        try {
            logger.info("Sending message to DLQ...");
            rabbitTemplate.convertAndSend("dlx_exchange", "dead_letter_queue", rawMessage);
        } catch (Exception e) {
            logger.error("Failed to send message to DLQ: {}", rawMessage, e);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}