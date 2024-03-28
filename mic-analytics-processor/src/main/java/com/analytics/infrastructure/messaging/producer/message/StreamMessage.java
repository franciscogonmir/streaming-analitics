package com.analytics.infrastructure.messaging.producer.message;


import java.util.List;


public record StreamMessage(String version, List<DataStreamMessage> dataStreams) {
}
