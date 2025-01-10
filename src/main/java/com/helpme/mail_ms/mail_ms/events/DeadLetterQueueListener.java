package com.helpme.mail_ms.mail_ms.events;

import com.helpme.mail_ms.mail_ms.rabbitmq.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterQueueListener {

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterQueueListener.class);

//    @RabbitListener(queues = "#{rabbitMQConfig.getDlqQueue()}")
//    public void processDeadLetter(String message) {
//       logger.info("Received a new message in DLQ");
//    }
}
