package com.analytics.domain.producer;

import com.analytics.domain.entities.Stream;

public interface SendData {

     void send(Stream dataStream);
}
