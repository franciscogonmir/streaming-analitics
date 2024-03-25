package com.analytics.infrastructure.messaging.producer.Message;


import java.util.List;


public record StreamMessage(String version, List<DataStreamMessage> dataStreams) {
}
