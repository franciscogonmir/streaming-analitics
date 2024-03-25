package com.analytics.infrastructure.consumer;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.service.StatisticsCalculatorService;
import com.analytics.domain.service.StatisticsRepositoryService;
import com.analytics.infrastructure.mapper.message.MessageMapper;
import com.analytics.infrastructure.producer.Message.StreamMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumerDataStream  {

    private final StatisticsRepositoryService statisticsRepositoryServiceService;

    private final MessageMapper mapper;

    private final MessageConverter messageConverter;
    @RabbitListener(queues = "myQueue",containerFactory = "myRabbitListenerContainerFactory")
    public void consumeDataStream( List<Message> messages) {
        log.info(":: Received {} messages ::", messages.size());
        List<StreamMessage> messagesToConsume = new ArrayList<>();
        for (Message message : messages) {
            StreamMessage streamMessage = (StreamMessage) messageConverter.fromMessage(message);
            messagesToConsume.add(streamMessage);
        }
        List<Stream> streams = this.mapper.toStreamsDomain(messagesToConsume);
        this.statisticsRepositoryServiceService.saveStatistics(streams);
    }
}
