package com.analytics.infrastructure.api.mapper;


import com.analytics.domain.entities.DataStream;
import com.analytics.domain.entities.Stream;
import com.analytics.infrastructure.api.dto.DataStreamDto;
import com.analytics.infrastructure.api.dto.StreamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataStreamMapper {

    @Mapping(target="dataStreams",source = "datastreams")
    Stream toDataStreamDomain(StreamDto dto);

    @Mapping(target="dataPoints",source = "datapoints")
    DataStream toDataStreamDomain(DataStreamDto dto);
}
