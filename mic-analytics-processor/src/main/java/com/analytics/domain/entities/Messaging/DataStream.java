package com.analytics.domain.entities.Messaging;


import lombok.Builder;

import java.util.List;
@Builder
public record DataStream(String id, String feed, List<DataPoint> dataPoints) {
}
