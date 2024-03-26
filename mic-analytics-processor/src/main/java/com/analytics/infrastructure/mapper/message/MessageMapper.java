package com.analytics.infrastructure.mapper.message;

import com.analytics.domain.entities.Messaging.DataPoint;
import com.analytics.domain.entities.Messaging.DataStream;
import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.infrastructure.messaging.producer.Message.DataPointMessage;
import com.analytics.infrastructure.messaging.producer.Message.DataStreamMessage;
import com.analytics.infrastructure.messaging.producer.Message.StreamMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    DataPointMessage toDataPointMessage(DataPoint dataPointDomain);

    DataStreamMessage toDataStreamMessage(DataStream dataStreamDomain);

    StreamMessage toStreamMessage(Stream streamDomain);

    DataPoint toDataPointDomain(DataPointMessage dataPointMessage);

    DataStream toDataStreamDomain(DataStream dataStreamMessage);

    Stream toStreamDomain(StreamMessage streamDomainMessage);

    List<Stream> toStreamsDomain(List<StreamMessage> messages);
}
