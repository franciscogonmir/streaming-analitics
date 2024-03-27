package com.analytics.domain.usecase;

import com.analytics.domain.entities.persistence.Stats;

import java.util.List;

public interface ReadStatsUseCase {

    Stats getStatsById(final String id);

    List<Stats> filterMean(final String operator, final double value);

    List<Stats> filterMinValue(final String operator, final double value);


    List<Stats> filterMeanAndMinValue(final String operator, final double meanValue, final double minValue);
}
