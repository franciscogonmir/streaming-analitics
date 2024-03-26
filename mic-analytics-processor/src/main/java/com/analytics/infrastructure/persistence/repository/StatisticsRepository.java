package com.analytics.infrastructure.persistence.repository;

import com.analytics.infrastructure.persistence.model.StatisticsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatisticsRepository extends MongoRepository<StatisticsModel,String> {

    List<StatisticsModel> findByMeanLessThan(double mean);

    List<StatisticsModel> findByMeanGreaterThan(double mean);

    List<StatisticsModel> findByMaxValueLessThan(double mean);

    List<StatisticsModel> findByMaxValueGreaterThan(double mean);
}
