package com.analytics.domain.producer;

import com.analytics.domain.entities.Stream;

public interface ProducerDataStream {

     void send(Stream dataStream);
}
