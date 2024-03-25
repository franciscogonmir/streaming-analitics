package com.analytics.infrastructure.api.mapper;

import com.analytics.domain.entities.persistence.Stats;
import com.analytics.infrastructure.api.dto.StatsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatsMapper {

    StatsDto toStatsDto(Stats statsDomainDto);

    List<StatsDto> toStatsListDto(List<Stats> statsDomainDto);
}
