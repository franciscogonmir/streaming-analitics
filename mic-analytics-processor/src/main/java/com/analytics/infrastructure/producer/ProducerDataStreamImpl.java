package com.analytics.infrastructure.producer;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.producer.ProducerDataStream;
import com.analytics.infrastructure.mapper.message.MessageMapper;
import com.analytics.infrastructure.producer.Message.StreamMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class ProducerDataStreamImpl implements ProducerDataStream {

    private final AmqpTemplate template;

    private final MessageMapper mapper;

    @Override
    public void send(Stream dataStream) {
        log.info(":: Sending message-> {} ::",dataStream.toString());
        StreamMessage message = mapper.toStreamMessage(dataStream);
        this.template.convertAndSend("exchange", "routingkey",message);
    }
}
