package com.analytics.infrastructure.producer.Message;


import java.io.Serializable;
import java.util.List;

public record DataStreamMessage(String id, String feed, List<DataPointMessage> dataPoints) {
}
