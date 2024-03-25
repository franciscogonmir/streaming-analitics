package com.analytics.infrastructure.messaging.producer;

import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.domain.messaging.ProducerDataStream;
import com.analytics.infrastructure.mapper.message.MessageMapper;
import com.analytics.infrastructure.messaging.producer.Message.StreamMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ProducerDataStreamImpl implements ProducerDataStream {

    private final AmqpTemplate template;

    private final MessageMapper mapper;

    @Override
    public void send(Stream dataStream) {
        log.info(":: Sending message-> {} ::", dataStream.toString());
        StreamMessage message = mapper.toStreamMessage(dataStream);
        this.template.convertAndSend("exchange", "routingkey", message);
    }
}
