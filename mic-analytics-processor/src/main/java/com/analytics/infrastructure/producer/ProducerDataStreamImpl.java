package com.analytics.infrastructure.producer;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.producer.ProducerDataStream;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ProducerDataStreamImpl implements ProducerDataStream {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;
    @Override
    //TODO enviar mesnsaje de infraestrucutura
    public void send(Stream dataStream) {
        rabbitTemplate.convertAndSend(queue.getName(),dataStream);
    }
}
