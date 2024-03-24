package com.analytics.infrastructure.service;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.entities.persistence.Stats;
import com.analytics.domain.service.StatisticsCalculatorService;
import com.analytics.domain.service.StatisticsRepositoryService;
import com.analytics.infrastructure.mapper.persistence.StatisticsMapper;
import com.analytics.infrastructure.persistence.model.StatisticsModel;
import com.analytics.infrastructure.persistence.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
