package com.analytics.domain.entities;


import java.io.Serializable;
import java.util.List;

public record DataStream(String id, String feed, List<DataPoint> dataPoints) implements Serializable {
}
