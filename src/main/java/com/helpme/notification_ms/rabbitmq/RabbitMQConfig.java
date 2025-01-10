package com.helpme.notification_ms.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.notification}")
    private String NOTIFICATION_QUEUE;

    @Value("${spring.rabbitmq.queue.dlq}")
    private String DLQ;

    public static final String MAIN_EXCHANGE = "main_exchange";
    public static final String DLX_EXCHANGE = "dlx_exchange";

    public String getNotificationQueue() {
        return this.NOTIFICATION_QUEUE;
    }

    public String getDlqQueue() {
        return this.DLQ;
    }

    @Bean
    public DirectExchange mainExchange() {
        return new DirectExchange(MAIN_EXCHANGE);
    }

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(this.NOTIFICATION_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE) // Route failed messages to DLX
                .withArgument("x-dead-letter-routing-key", DLQ)       // DLQ routing key
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding notificationQueueBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(mainExchange())
                .with(this.NOTIFICATION_QUEUE);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(dlxExchange()).with(DLQ);
    }

}
