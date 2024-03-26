package com.analytics.infrastructure.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record StreamDto(@NotNull String version,@Valid @NotNull List<DataStreamDto> datastreams) {

}
