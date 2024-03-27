package com.analytics.infrastructure.api.controller;

import com.analytics.domain.usecase.ReadStatsUseCase;
import com.analytics.infrastructure.api.dto.OperatorDto;
import com.analytics.infrastructure.api.dto.StatsDto;
import com.analytics.infrastructure.api.mapper.StatsMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class ReadStatisticsController {


    private final StatsMapper statsMapper;

    private final ReadStatsUseCase readStatsUseCase;


    @GetMapping("/filter/id/{id}")
    public ResponseEntity<StatsDto> getStatsById(@PathVariable final String id) {
        var response = this.statsMapper.toStatsDto(readStatsUseCase.getStatsById(id));
        log.info(":: result obtained by id -> {} ::", response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/filter/mean")
    public ResponseEntity<List<StatsDto>> filterByMean(@RequestParam @NotNull final OperatorDto operator,
                                                       @RequestParam @NotNull final double value) {
        var operatorName = operator.name();
        var response = this.statsMapper.toStatsListDto(this.readStatsUseCase.filterMean(operatorName.toLowerCase(), value));
        response.forEach(statsDto -> log.info(":: Filtered result by mean {} {} -> {} ::",
                operator,
                value,
                statsDto.toString()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter/minvalue")
    public ResponseEntity<List<StatsDto>> filterByMinValue(@RequestParam @NotNull final OperatorDto operator,
                                                           @RequestParam @NotNull final double value) {
        var operatorName = operator.name();
        var response = this.statsMapper.toStatsListDto(this.readStatsUseCase.filterMinValue(operatorName.toLowerCase(), value));
        response.forEach(statsDto -> log.info(":: Filtered result by minimum value {} {} -> {} ::",
                operator,
                value,
                statsDto.toString()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter/mean/and/minvalue")
    public ResponseEntity<List<StatsDto>> filterByMeanAndMinValue(@RequestParam @NotNull final OperatorDto operator,
                                                                  @RequestParam @NotNull double meanValue,
                                                                  @RequestParam @NotNull double minValue) {
        var operatorName = operator.name();
        var response = this.statsMapper.toStatsListDto(this.readStatsUseCase.filterMeanAndMinValue(operatorName.toLowerCase(), meanValue, minValue));
        response.forEach(statsDto -> log.info(":: Filtered result by mean {} and minimum value {} with operator {} -> {} ::",
                meanValue,
                minValue,
                operator,
                statsDto.toString()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
