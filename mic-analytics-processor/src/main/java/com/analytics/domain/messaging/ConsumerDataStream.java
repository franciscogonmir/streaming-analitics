package com.analytics.domain.messaging;

import org.springframework.amqp.core.Message;

import java.util.List;

public interface ConsumerDataStream {

    void consumeDataStream(final List<Message> messages);
}
