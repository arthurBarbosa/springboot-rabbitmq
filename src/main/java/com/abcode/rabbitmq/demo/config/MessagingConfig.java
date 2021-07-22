package com.abcode.rabbitmq.demo.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MessagingConfig {

    public static final String ABCODE_QUEUE = "abcode_queue";
    public static final String ABCODE_EXCHANGE = "abcode_exchange";
    public static final String ABCODE_ROUTING_KEY = "abcode_routingKey";

    @Bean
    public Queue queue() {
        return new Queue(ABCODE_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ABCODE_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ABCODE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

