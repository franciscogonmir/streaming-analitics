package com.analytics.infrastructure.api.dto;

import java.util.List;

public record StreamDto(String version, List<DataStreamDto> datastreams) {

}
