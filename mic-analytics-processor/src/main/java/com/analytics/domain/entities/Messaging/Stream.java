package com.analytics.domain.entities.Messaging;


import lombok.Builder;

import java.util.List;

@Builder
public record Stream(String version, List<DataStream> dataStreams) {

}
