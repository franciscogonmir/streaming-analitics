package com.analytics.infrastructure.producer.Message;

import java.io.Serializable;

public record DataPointMessage(Long at, Integer value) {
}
