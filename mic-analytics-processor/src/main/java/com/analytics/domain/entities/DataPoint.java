package com.analytics.domain.entities;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record DataPoint (Long at, Integer value) {
}
