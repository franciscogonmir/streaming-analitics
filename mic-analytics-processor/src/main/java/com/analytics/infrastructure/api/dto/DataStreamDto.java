package com.analytics.infrastructure.api.dto;

import java.util.List;

public record DataStreamDto(String id, String feed, List<DataPointDto> datapoints) {
}
