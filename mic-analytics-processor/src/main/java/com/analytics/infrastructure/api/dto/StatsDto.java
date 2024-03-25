package com.analytics.infrastructure.api.dto;

import java.util.List;

public record StatsDto(String id, long at, double mean, double median, List<Integer> mode, double standardDeviation,
                       double firstQuartile, double thirdQuartile, double maxValue, double minValue) {
}
