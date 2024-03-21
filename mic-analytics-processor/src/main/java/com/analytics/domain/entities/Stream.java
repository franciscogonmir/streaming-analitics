package com.analytics.domain.entities;


import java.io.Serializable;
import java.util.List;

public record Stream(String version, List<DataStream> dataStreams) implements Serializable {
}
