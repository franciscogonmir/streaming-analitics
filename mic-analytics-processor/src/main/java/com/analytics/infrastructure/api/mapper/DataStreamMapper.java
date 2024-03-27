package com.analytics.infrastructure.api.mapper;


import com.analytics.domain.entities.Messaging.DataStream;
import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.infrastructure.api.dto.DataStreamDto;
import com.analytics.infrastructure.api.dto.StreamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataStreamMapper {

    @Mapping(target = "dataStreams", source = "datastreams")
    Stream toDataStreamDomain(final StreamDto dto);

    @Mapping(target = "dataPoints", source = "datapoints")
    DataStream toDataStreamDomain(final DataStreamDto dto);
}
