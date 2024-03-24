package com.analytics.infrastructure.consumer;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.service.StatisticsCalculatorService;
import com.analytics.infrastructure.mapper.message.MessageMapper;
import com.analytics.infrastructure.producer.Message.StreamMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RabbitListener(queues = "myQueue",containerFactory = "myRabbitListenerContainerFactory")
@Component
public class ConsumerDataStream  {

    @Autowired
    StatisticsCalculatorService statisticsCalculatorService;
    @Autowired
    MessageMapper mapper;
    @RabbitHandler
    public void consumeDataStream(List<StreamMessage> messages) {
        List<Stream> streams = this.mapper.toStreamsDomain(messages);

        System.out.println("the median messages is : " + statisticsCalculatorService.calculateMean(streams));
    }
}
