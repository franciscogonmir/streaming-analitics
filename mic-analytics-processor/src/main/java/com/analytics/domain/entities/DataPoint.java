package com.analytics.domain.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public record DataPoint (Long at, Integer value) implements Serializable {
}
