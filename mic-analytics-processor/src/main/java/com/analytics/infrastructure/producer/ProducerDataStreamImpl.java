package com.analytics.infrastructure.producer;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.producer.ProducerDataStream;
import com.analytics.infrastructure.mapper.message.MessageMapper;
import com.analytics.infrastructure.producer.Message.StreamMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ProducerDataStreamImpl implements ProducerDataStream {

    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    private final MessageMapper mapper;

    @Override
    public void send(Stream dataStream) {
        log.info(":: Sending message-> {} ::",dataStream.toString());
        var message = mapper.toStreamMessage(dataStream);
        rabbitTemplate.convertAndSend(queue.getName(), message);

    }
}
