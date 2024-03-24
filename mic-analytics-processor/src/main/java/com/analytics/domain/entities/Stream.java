package com.analytics.domain.entities;


import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record Stream(String version, List<DataStream> dataStreams) {

}
