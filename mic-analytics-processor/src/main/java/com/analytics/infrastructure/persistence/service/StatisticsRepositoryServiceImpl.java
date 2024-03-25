package com.analytics.infrastructure.persistence.service;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.entities.persistence.Stats;
import com.analytics.domain.exception.StatsNotFoundException;
import com.analytics.domain.service.StatisticsCalculatorService;
import com.analytics.domain.service.StatisticsRepositoryService;
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
    public void saveStatistics(List<Stream> feed) {
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
    public Stats findStatsById(String id) {
        log.info(":: Search  stats with id -> {} ::",id);
        var statisticModel = this.statisticsRepository.findById(id).orElseThrow(() -> new StatsNotFoundException(id));
        return this.mapper.toStatsDomain(statisticModel);
    }

    @Override
    public List<Stats> findByMeanLessThan(double value) {
        log.info(":: Search  stats with main less than -> {} ::",value);
        var statisticsModel = this.statisticsRepository.findByMeanLessThan(value);
        return this.mapper.toStatsListDomain(statisticsModel);
    }

    @Override
    public List<Stats> findByMeanGreaterThan(double value) {
        log.info(":: Search  stats with main greater than -> {} ::",value);
        var statisticsModel = this.statisticsRepository.findByMeanGreaterThan(value);
        return this.mapper.toStatsListDomain(statisticsModel);
    }

    @Override
    public List<Stats> findByMaxValueLessThan(double value) {
        log.info(":: Search  stats with max value is less than -> {} ::",value);
        var statisticsModel = this.statisticsRepository.findByMaxValueLessThan(value);
        return this.mapper.toStatsListDomain(statisticsModel);
    }

    @Override
    public List<Stats> findByMaxValueGreaterThan(double value) {
        log.info(":: Search  stats with max value is greater than -> {} ::",value);
        var statisticsModel = this.statisticsRepository.findByMaxValueGreaterThan(value);
        return this.mapper.toStatsListDomain(statisticsModel);
    }

}
