package com.analytics.domain.service.repository;

import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.domain.entities.persistence.Stats;

import java.util.List;

public interface StatisticsRepositoryService {

    void saveStatistics(List<Stream> feed);

    Stats findStatsById(String id);

    List<Stats> findByMeanLessThan(double value);

    List<Stats> findByMeanGreaterThan(double value);

    List<Stats> findByMaxValueLessThan(double value);

    List<Stats> findByMaxValueGreaterThan(double value);

}
