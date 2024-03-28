package com.analytics.infrastructure.mapper.message;

import com.analytics.domain.entities.Messaging.DataPoint;
import com.analytics.domain.entities.Messaging.DataStream;
import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.infrastructure.messaging.producer.message.DataPointMessage;
import com.analytics.infrastructure.messaging.producer.message.DataStreamMessage;
import com.analytics.infrastructure.messaging.producer.message.StreamMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    DataPointMessage toDataPointMessage(final DataPoint dataPointDomain);

    DataStreamMessage toDataStreamMessage(final DataStream dataStreamDomain);

    StreamMessage toStreamMessage(final Stream streamDomain);

    DataPoint toDataPointDomain(final DataPointMessage dataPointMessage);

    DataStream toDataStreamDomain(final DataStream dataStreamMessage);

    Stream toStreamDomain(final StreamMessage streamDomainMessage);

    List<Stream> toStreamsDomain(final List<StreamMessage> messages);
}
