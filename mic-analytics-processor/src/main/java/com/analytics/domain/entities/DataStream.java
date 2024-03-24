package com.analytics.domain.entities;


import lombok.Builder;

import java.io.Serializable;
import java.util.List;
@Builder
public record DataStream(String id, String feed, List<DataPoint> dataPoints) {
}
