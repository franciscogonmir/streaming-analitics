package com.analytics.infrastructure.messaging.producer.message;

public record DataPointMessage(Long at, Integer value) {
}
