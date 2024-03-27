package com.analytics.infrastructure.messaging.config;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ListenerConfig {

    private final long receiveTimeout;

    private final boolean batchMode;

    private final boolean batchConsumer;

    private final int batchSize;


}
