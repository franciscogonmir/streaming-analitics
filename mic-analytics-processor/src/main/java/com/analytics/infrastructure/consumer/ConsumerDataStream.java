package com.analytics.infrastructure.consumer;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.service.StatisticsCalculatorService;
import com.analytics.domain.service.StatisticsRepositoryService;
import com.analytics.infrastructure.mapper.message.MessageMapper;
import com.analytics.infrastructure.producer.Message.StreamMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RabbitListener(queues = "myQueue",containerFactory = "myRabbitListenerContainerFactory")
@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumerDataStream  {

    private final StatisticsRepositoryService statisticsRepositoryServiceService;

    private final MessageMapper mapper;
    @RabbitHandler
    public void consumeDataStream(List<StreamMessage> messages) {
        log.info(":: Received {} messages ::", messages.size());
        List<Stream> streams = this.mapper.toStreamsDomain(messages);
        this.statisticsRepositoryServiceService.saveStatistics(streams);
    }
}
