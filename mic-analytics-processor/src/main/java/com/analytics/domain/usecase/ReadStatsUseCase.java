package com.analytics.domain.usecase;

import com.analytics.domain.entities.persistence.Stats;

import java.util.List;

public interface ReadStatsUseCase {

    Stats getStatsById(String id);

    List<Stats> filterStatsByField(String field,String operator, double value);

}
