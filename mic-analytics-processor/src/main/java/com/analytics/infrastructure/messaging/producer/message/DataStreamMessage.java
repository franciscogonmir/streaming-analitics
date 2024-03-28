package com.analytics.infrastructure.messaging.producer.message;


import java.util.List;

public record DataStreamMessage(String id, String feed, List<DataPointMessage> dataPoints) {
}
