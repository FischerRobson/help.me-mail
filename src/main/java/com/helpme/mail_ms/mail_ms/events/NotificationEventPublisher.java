package com.helpme.mail_ms.mail_ms.events;

import com.helpme.mail_ms.mail_ms.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    public void sendEmailEvent(String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.getNotificationQueue(), message);
    }
}
