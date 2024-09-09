package com.helpme.mail_ms.mail_ms.rabbitmq;

import com.helpme.mail_ms.mail_ms.constants.Constants;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private Constants constants;

    @Bean
    public Queue emailQueue() {
        return new Queue(constants.EMAIL_QUEUE, false);
    }
}
