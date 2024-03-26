package com.analytics.infrastructure.api.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DataPointDto(Long at, @NotNull Integer value) {
}
