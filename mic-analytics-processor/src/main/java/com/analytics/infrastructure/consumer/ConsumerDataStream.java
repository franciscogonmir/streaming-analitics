package com.analytics.infrastructure.consumer;

import com.analytics.domain.entities.Stream;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@RabbitListener(queues = "myQueue",containerFactory = "myRabbitListenerContainerFactory")
@Component
public class ConsumerDataStream  {

    @RabbitHandler
    public void consumeDataStream(List<Stream> dataStream) {

        System.out.println("Message read from myQueue : " + dataStream.size());
    }
}
