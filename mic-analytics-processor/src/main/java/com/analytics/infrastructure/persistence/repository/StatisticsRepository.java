package com.analytics.infrastructure.persistence.repository;

import com.analytics.infrastructure.persistence.model.StatisticsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatisticsRepository extends MongoRepository<StatisticsModel,String> {
    //filter by mean
    List<StatisticsModel> findByMean(double value);
    List<StatisticsModel> findByMeanGreaterThan(double value);
    List<StatisticsModel> findByMeanGreaterThanEqual(double value);
    List<StatisticsModel> findByMeanLessThan(double value);
    List<StatisticsModel> findByMeanLessThanEqual(double value);

    //filter by min value
    List<StatisticsModel> findByMinValue(double minValue);
    List<StatisticsModel> findByMinValueGreaterThan(double minValue);
    List<StatisticsModel> findByMinValueGreaterThanEqual(double minValue);
    List<StatisticsModel> findByMinValueLessThan(double minValue);
    List<StatisticsModel> findByMinValueLessThanEqual(double minValue);

    // filter by mean and minValue
    List<StatisticsModel> findByMeanAndMinValue(double mean, double minValue);
    List<StatisticsModel> findByMeanGreaterThanAndMinValueGreaterThan(double mean, double minValue);
    List<StatisticsModel> findByMeanGreaterThanEqualAndMinValueGreaterThanEqual(double mean, double minValue);
    List<StatisticsModel> findByMeanLessThanAndMinValueLessThan(double mean, double minValue);
    List<StatisticsModel> findByMeanLessThanEqualAndMinValueLessThanEqual(double mean, double minValue);
}
