package com.analytics.application.usecases;

import com.analytics.domain.entities.persistence.Stats;
import com.analytics.domain.service.repository.StatisticsRepositoryService;
import com.analytics.domain.usecase.ReadStatsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ReadStatsUseCaseImpl implements ReadStatsUseCase {

    private final StatisticsRepositoryService repositoryService;

    @Override
    public Stats getStatsById(final String id) {
        return repositoryService.findStatsById(id);
    }

    @Override
    public List<Stats> filterMean(final String operator, final double value) {
        return this.repositoryService.findStatsByMeanAndOperator(operator, value);
    }

    @Override
    public List<Stats> filterMinValue(final String operator, final double value) {
        return this.repositoryService.findStatsByMinValueAndOperator(operator, value);
    }

    @Override
    public List<Stats> filterMeanAndMinValue(final String operator, final double meanValue, final double minValue) {
        return this.repositoryService.findStatsByMeanWithMinValueAndOperator(operator, meanValue, minValue);

    }
}
