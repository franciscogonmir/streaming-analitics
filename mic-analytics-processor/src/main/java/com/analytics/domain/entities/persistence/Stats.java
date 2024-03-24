package com.analytics.domain.entities.persistence;

import lombok.*;

import java.util.List;

@Builder
public record Stats(String id,long at,double mean,double median,List<Integer> mode,double standardDeviation,double firstQuartile,
                    double thirdQuartile,double maxValue,double minValue) {
}
