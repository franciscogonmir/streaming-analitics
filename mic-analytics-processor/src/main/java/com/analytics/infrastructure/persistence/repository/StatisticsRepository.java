package com.analytics.infrastructure.persistence.repository;

import com.analytics.infrastructure.persistence.model.StatisticsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<StatisticsModel,String> {
}
