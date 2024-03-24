package com.analytics.infrastructure.producer.Message;


import java.io.Serializable;
import java.util.List;

public record StreamMessage(String version, List<DataStreamMessage> dataStreams) implements Serializable {
}
