package com.analytics.application.usecases;

import com.analytics.domain.entities.persistence.Stats;
import com.analytics.domain.exception.InvalidParameterException;
import com.analytics.domain.service.repository.StatisticsRepositoryService;
import com.analytics.domain.usecase.ReadStatsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
public class ReadStatsUseCaseImpl implements ReadStatsUseCase {

    private final StatisticsRepositoryService repositoryService;

    @Override
    public Stats getStatsById(String id) {
        return repositoryService.findStatsById(id);
    }

    @Override
    public List<Stats> filterStatsByField(String field, String operator, double value) {
        String methodName = "findBy" + capitalize(field) + capitalize(operator);
        try {
            return (List<Stats>) repositoryService.getClass().getMethod(methodName, double.class).invoke(repositoryService, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            log.error(":: Parameter not permitted ::");
            throw new InvalidParameterException(operator);
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
