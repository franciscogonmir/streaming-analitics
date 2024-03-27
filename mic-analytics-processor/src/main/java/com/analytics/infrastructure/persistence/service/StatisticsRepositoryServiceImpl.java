package com.analytics.infrastructure.persistence.service;

import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.domain.entities.persistence.Stats;
import com.analytics.domain.exception.InvalidParameterException;
import com.analytics.domain.exception.StatsNotFoundException;
import com.analytics.domain.service.repository.StatisticsRepositoryService;
import com.analytics.domain.service.statistics.StatisticsCalculatorService;
import com.analytics.infrastructure.mapper.persistence.StatisticsMapper;
import com.analytics.infrastructure.persistence.model.StatisticsModel;
import com.analytics.infrastructure.persistence.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsRepositoryServiceImpl implements StatisticsRepositoryService {

    private final StatisticsCalculatorService statisticsCalculatorService;

    private final StatisticsRepository statisticsRepository;

    private final StatisticsMapper mapper;

    @Override
    @Transactional
    public void saveStatistics(final List<Stream> feed) {
        log.info(":: Calculate statistics ::");
        Stats stats = Stats.builder()
                .id(UUID.randomUUID().toString())
                .at(Instant.now().toEpochMilli())
                .mean(this.statisticsCalculatorService.calculateMean(feed))
                .median(this.statisticsCalculatorService.getMedian(feed))
                .mode(this.statisticsCalculatorService.getMode(feed))
                .standardDeviation(this.statisticsCalculatorService.calculateStandardDeviation(feed))
                .firstQuartile(this.statisticsCalculatorService.calculateFirstQuartile(feed))
                .thirdQuartile(this.statisticsCalculatorService.calculateThirdQuartile(feed))
                .maxValue(this.statisticsCalculatorService.calculateMaxValue(feed))
                .minValue(this.statisticsCalculatorService.calculateMinValue(feed))
                .build();
        StatisticsModel statistics = this.mapper.toStatistics(stats);
        log.info(":: save statistics -> {} ::", statistics.toString());
        this.statisticsRepository.save(statistics);
    }

    @Override
    public Stats findStatsById(final String id) {
        log.info(":: Search  stats with id -> {} ::", id);
        var statisticModel = this.statisticsRepository.findById(id).orElseThrow(() -> {
            log.error(":: Could not find id -> {} :: ", id);
            return new StatsNotFoundException(id);
        });
        return this.mapper.toStatsDomain(statisticModel);
    }

    @Override
    public List<Stats> findStatsByMeanAndOperator(final String operator, final Double value) {
        return switch (operator) {
            case "eq" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMean(value));
            case "lt" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMeanLessThan(value));
            case "lte" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMeanLessThanEqual(value));
            case "gt" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMeanGreaterThan(value));
            case "gte" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMeanGreaterThanEqual(value));
            default -> throw new InvalidParameterException(operator);
        };
    }

    @Override
    public List<Stats> findStatsByMinValueAndOperator(final String operator, final Double value) {
        return switch (operator) {
            case "eq" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMinValue(value));
            case "lt" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMinValueLessThan(value));
            case "lte" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMinValueLessThanEqual(value));
            case "gt" -> this.mapper.toStatsListDomain(this.statisticsRepository.findByMinValueGreaterThan(value));
            case "gte" ->
                    this.mapper.toStatsListDomain(this.statisticsRepository.findByMinValueGreaterThanEqual(value));
            default -> throw new InvalidParameterException(operator);
        };
    }

    @Override
    public List<Stats> findStatsByMeanWithMinValueAndOperator(final String operator, final Double meanValue, final Double minValue) {
        return switch (operator) {
            case "eq" ->
                    this.mapper.toStatsListDomain(this.statisticsRepository.findByMeanAndMinValue(meanValue, minValue));
            case "lt" -> this.mapper.toStatsListDomain(this.statisticsRepository
                    .findByMeanLessThanAndMinValueLessThan(meanValue, minValue));
            case "lte" -> this.mapper.toStatsListDomain(this.statisticsRepository
                    .findByMeanLessThanEqualAndMinValueLessThanEqual(meanValue, minValue));
            case "gt" -> this.mapper.toStatsListDomain(this.statisticsRepository
                    .findByMeanGreaterThanAndMinValueGreaterThan(meanValue, minValue));
            case "gte" -> this.mapper.toStatsListDomain(this.statisticsRepository
                    .findByMeanGreaterThanEqualAndMinValueGreaterThanEqual(meanValue, minValue));
            default -> throw new InvalidParameterException(operator);
        };
    }


}
