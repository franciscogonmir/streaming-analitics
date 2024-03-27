package com.analytics.domain.service.repository;

import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.domain.entities.persistence.Stats;

import java.util.List;

public interface StatisticsRepositoryService {

    void saveStatistics(final List<Stream> feed);

    Stats findStatsById(final String id);

    List<Stats> findStatsByMeanAndOperator(final String operator, final Double value);

    List<Stats> findStatsByMinValueAndOperator(final String operator, final Double value);

    List<Stats> findStatsByMeanWithMinValueAndOperator(final String operator, final Double meanValue, final Double minValue);
}
