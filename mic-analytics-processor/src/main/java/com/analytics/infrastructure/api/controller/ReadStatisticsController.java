package com.analytics.infrastructure.api.controller;

import com.analytics.domain.usecase.ReadStatsUseCase;
import com.analytics.infrastructure.api.dto.StatsDto;
import com.analytics.infrastructure.api.mapper.StatsMapper;
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


    @GetMapping("/{id}")
    public ResponseEntity<StatsDto> getStatsById(@PathVariable String id) {
        var response = this.statsMapper.toStatsDto(readStatsUseCase.getStatsById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/field/{field}")
    public ResponseEntity<List<StatsDto>> getStatsByFields(@PathVariable String field, @RequestParam String operator,
                                                           @RequestParam double value) {
        var response = this.statsMapper.toStatsListDto(this.readStatsUseCase.filterStatsByField(field, operator, value));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
