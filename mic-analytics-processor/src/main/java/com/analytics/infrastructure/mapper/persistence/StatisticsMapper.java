package com.analytics.infrastructure.mapper.persistence;

import com.analytics.domain.entities.persistence.Stats;
import com.analytics.infrastructure.persistence.model.StatisticsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {

    StatisticsModel toStatistics(Stats statsDomain);
}
