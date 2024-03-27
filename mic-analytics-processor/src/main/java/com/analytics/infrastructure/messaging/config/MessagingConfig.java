package com.analytics.infrastructure.messaging.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MessagingConfig {

    private final RabbitProperties rabbitProperties;

    @Bean
    public Queue myQueue() {
        return new Queue(this.rabbitProperties.getQueue(), true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(this.rabbitProperties.getExchange());
    }

    @Bean
    Binding binding(final Queue queue, final DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(this.rabbitProperties.getRoutingKey());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
        ListenerConfig listenerConfig = this.rabbitProperties.getListenerConfig();
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setReceiveTimeout(listenerConfig.getReceiveTimeout());
        factory.setBatchListener(listenerConfig.isBatchMode());
        factory.setConsumerBatchEnabled(listenerConfig.isBatchConsumer());
        factory.setBatchSize(listenerConfig.getBatchSize());
        return factory;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.rabbitProperties.getHost());
        connectionFactory.setPort(this.rabbitProperties.getPort());
        connectionFactory.setUsername(this.rabbitProperties.getUsername());
        connectionFactory.setPassword(this.rabbitProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate template(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(this.jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
