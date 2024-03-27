package com.analytics.infrastructure.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record DataStreamDto(String id, String feed, @Valid @NotNull List<DataPointDto> datapoints) {
}
