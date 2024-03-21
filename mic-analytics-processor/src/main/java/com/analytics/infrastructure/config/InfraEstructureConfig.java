package com.analytics.infrastructure.config;

import com.analytics.domain.producer.SendData;
import com.analytics.infrastructure.producer.ProducerDataStream;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraEstructureConfig {

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", true);
    }

    @Bean
    public SendData producer(){
        return new ProducerDataStream();
    }

    //TODO ver si la config me la puedo llevar al application.yml
    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setReceiveTimeout(10000L);
        factory.setBatchListener(true);
        factory.setConsumerBatchEnabled(true);
        factory.setBatchSize(5);
        return factory;
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        // Otras configuraciones según sea necesario
        return connectionFactory;
    }

}
