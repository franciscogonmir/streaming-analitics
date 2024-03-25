package com.analytics.domain.messaging;

import com.analytics.domain.entities.Messaging.Stream;

public interface ProducerDataStream {

     void send(Stream dataStream);
}
